package com.reksio.restbackend.bootstrap;

import com.reksio.restbackend.user.Role;
import com.reksio.restbackend.user.User;
import com.reksio.restbackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class Bootstrap {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public BCryptPasswordEncoder encoder;

    @PostConstruct
    public void insertUser(){
        userRepository.deleteAll();

        User user = User.builder()
                .email("michal@gmail.com")
                .isActive(true)
                .password(encoder.encode("password"))
                .roles(List.of(Role.ADMIN))
                .build();

        userRepository.save(user);
    }
}
