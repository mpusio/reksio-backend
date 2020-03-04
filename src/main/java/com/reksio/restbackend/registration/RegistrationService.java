package com.reksio.restbackend.registration;

import com.reksio.restbackend.collection.user.Role;
import com.reksio.restbackend.collection.user.User;
import com.reksio.restbackend.collection.user.UserRepository;
import com.reksio.restbackend.exception.user.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User registerUser(RegistrationRequest request) {
        checkEmailAppearsInDatabase(request.getEmail());

        User user = User.builder()
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .isActive(false)
                .roles(List.of(Role.USER))
                .build();

        return userRepository.insert(user);
    }

    public void checkEmailAppearsInDatabase(String email){
        userRepository.findByEmail(email)
                .ifPresent(user -> {throw new UserExistException("This email exist in database");});
    }
}
