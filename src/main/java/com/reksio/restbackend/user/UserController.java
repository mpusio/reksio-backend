package com.reksio.restbackend.user;

import com.reksio.restbackend.exception.UserInvalidFieldException;
import com.reksio.restbackend.security.JwtUtil;
import com.reksio.restbackend.user.dto.UserUpdatePasswordRequest;
import com.reksio.restbackend.user.dto.UserUpdateProfileRequest;
import com.reksio.restbackend.user.dto.UserProfileResponse;
import org.springframework.validation.BindingResult;
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
    public UserProfileResponse updateProfile(@Valid @RequestBody UserUpdateProfileRequest userUpdateProfileRequest, BindingResult result,  HttpServletRequest servletRequest){
        String token = servletRequest.getHeader("Authorization");
        String email = JwtUtil.fetchEmail(token);

        if(result.hasErrors()){
            throw new UserInvalidFieldException(
                    result
                            .getFieldErrors()
                            .stream()
                            .map(f -> f.getField() + ": " + f.getDefaultMessage())
                            .reduce((a, b) -> a + b)
                            .toString());
        }

        return userService.updateUserDetails(email, userUpdateProfileRequest);
    }

    @PutMapping("/user/password")
    public UserProfileResponse changePassword(@Valid @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest, BindingResult result,  HttpServletRequest servletRequest){
        if(result.hasErrors()){
            throw new UserInvalidFieldException(
                    result
                            .getFieldErrors()
                            .stream()
                            .map(f -> f.getField() + ": " + f.getDefaultMessage())
                            .reduce((a, b) -> a + b)
                            .toString());
        }

        String token = servletRequest.getHeader("Authorization");
        String email = JwtUtil.fetchEmail(token);

        return userService.updatePassword(email, userUpdatePasswordRequest);
    }
}
