package com.reksio.restbackend.user;

import com.reksio.restbackend.collection.user.ActivationToken;
import com.reksio.restbackend.collection.user.ResetPasswordToken;
import com.reksio.restbackend.collection.user.User;
import com.reksio.restbackend.collection.user.UserRepository;
import com.reksio.restbackend.exception.user.TokenExpiredException;
import com.reksio.restbackend.exception.user.UserNotExistException;
import com.reksio.restbackend.exception.user.UserNotEqualsPasswordException;
import com.reksio.restbackend.user.dto.UserProfileResponse;
import com.reksio.restbackend.user.dto.UserUpdatePasswordRequest;
import com.reksio.restbackend.user.dto.UserUpdateProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public UserProfileResponse fetchUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotExistException(email));

        return UserProfileResponse.convertFromUser(user);
    }

    public UserProfileResponse updateUserDetails(String email, UserUpdateProfileRequest updateRequest) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotExistException(email));

        user.setFirstName(nullChecker(updateRequest.getFirstName(), user.getFirstName()));
        user.setLastName(nullChecker(updateRequest.getLastName(), user.getLastName()));
        user.setDescription(nullChecker(updateRequest.getDescription(), user.getDescription()));
        user.setImage(nullChecker(updateRequest.getImage(), user.getImage()));

        userRepository.save(user);

        return UserProfileResponse.convertFromUser(user);
    }

    private <T> T nullChecker(T fieldToInsert, T actualField){
        if (fieldToInsert == null){
            return actualField;
        }
        return fieldToInsert;
    }

    public UserProfileResponse changePassword(String email, UserUpdatePasswordRequest updateRequest) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotExistException(email));

        String newPasswordEncoded = encoder.encode(updateRequest.getNewPassword());

        if (encoder.matches(updateRequest.getOldPassword(), user.getPassword())){
            user.setPassword(newPasswordEncoded);
        }
        else throw new UserNotEqualsPasswordException("Old password is incorrect.");

        userRepository.save(user);

        return UserProfileResponse.convertFromUser(user);
    }

    public void setActivationToken(String email, UUID activationToken) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistException("Cannot find user by email " + email));

        user.setActivationToken(new ActivationToken(activationToken, LocalDateTime.now().plusDays(1)));
        userRepository.save(user);
    }

    public void activateAccount(UUID token) {
        User user = userRepository.findByActivationToken_Uuid(token)
                .orElseThrow(() -> new UserNotExistException("Cannot find user by activation token " + token));

        if (user.getActivationToken().getExpirationTime().isBefore(LocalDateTime.now())){
            throw new TokenExpiredException("Activation token has been expired.");
        }

        user.setActive(true);
        userRepository.save(user);
    }

    public void setResetPasswordToken(String email, UUID resetPasswordToken) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistException("Cannot find user by email " + email));

        user.setResetPasswordToken(new ResetPasswordToken(resetPasswordToken, LocalDateTime.now().plusMinutes(15)));
        userRepository.save(user);
    }

    public void resetPassword(UUID token, String newPassword){
        User user = userRepository.findByResetPasswordToken_Uuid(token)
                .orElseThrow(() -> new UserNotExistException("Cannot find user by reset password token " + token));

        if (user.getResetPasswordToken().getExpirationTime().isBefore(LocalDateTime.now())){
            throw new TokenExpiredException("Reset password token has been expired.");
        }

        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }

    public List<User> getActiveUsers() {
        return userRepository.findAllByIsActiveTrue();
    }
}
