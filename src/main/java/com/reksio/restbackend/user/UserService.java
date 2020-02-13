package com.reksio.restbackend.user;

import com.reksio.restbackend.collection.user.User;
import com.reksio.restbackend.collection.user.UserRepository;
import com.reksio.restbackend.exception.UserExistException;
import com.reksio.restbackend.exception.UserNotEqualsPasswordException;
import com.reksio.restbackend.user.dto.UserProfileResponse;
import com.reksio.restbackend.user.dto.UserUpdatePasswordRequest;
import com.reksio.restbackend.user.dto.UserUpdateProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserExistException("Cannot find user."));

        return UserProfileResponse.convertFromUser(user);
    }

    public UserProfileResponse updateUserDetails(String email, UserUpdateProfileRequest updateRequest) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserExistException("Cannot find user."));

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

    public UserProfileResponse updatePassword(String email, UserUpdatePasswordRequest updateRequest) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserExistException("Cannot find user."));

        String newPasswordEncoded = encoder.encode(updateRequest.getNewPassword());

        if (encoder.matches(updateRequest.getOldPassword(), user.getPassword())){
            user.setPassword(newPasswordEncoded);
        }
        else throw new UserNotEqualsPasswordException("Old password is incorrect.");

        userRepository.save(user);

        return UserProfileResponse.convertFromUser(user);
    }
}
