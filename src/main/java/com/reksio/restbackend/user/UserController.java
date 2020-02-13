package com.reksio.restbackend.user;

import com.reksio.restbackend.security.JwtUtil;
import com.reksio.restbackend.user.dto.UserRequest;
import com.reksio.restbackend.user.dto.UserProfileResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public UserProfileResponse selectProfile(@RequestParam String email){
        return userService.fetchUserByEmail(email);
    }

    @PutMapping("/user")
    public void updateProfile(@Valid @RequestBody UserRequest userRequest, HttpServletRequest servletRequest){
//        String token = servletRequest.getHeader("Authentication");
//        String email = JwtUtil.fetchEmail(token);

        throw new RuntimeException("Waiting for implementation");
    }

    @PutMapping("/user/password")
    public void changePassword(){
        throw new RuntimeException("Waiting for implementation");
    }

    @PutMapping("/user/email")
    public void changeEmail(){
        throw new RuntimeException("Waiting for implementation");
    }


    //add advertisement
    //add post (bloger)
}
