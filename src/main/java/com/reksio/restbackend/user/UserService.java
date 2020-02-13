package com.reksio.restbackend.user;

import com.reksio.restbackend.collection.user.User;
import com.reksio.restbackend.collection.user.UserRepository;
import com.reksio.restbackend.exception.UserExistException;
import com.reksio.restbackend.user.dto.UserProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserProfileResponse fetchUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserExistException("Cannot find user."));

        return UserProfileResponse.convertFromUser(user);
    }
}
