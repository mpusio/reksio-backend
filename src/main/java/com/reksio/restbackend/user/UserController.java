package com.reksio.restbackend.user;

import com.reksio.restbackend.exception.user.UserInvalidFieldException;
import com.reksio.restbackend.security.JwtUtil;
import com.reksio.restbackend.user.dto.UserProfileResponse;
import com.reksio.restbackend.user.dto.UserUpdatePasswordRequest;
import com.reksio.restbackend.user.dto.UserUpdateProfileRequest;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("Select user profile")
    @GetMapping("/user/{email}")
    public UserProfileResponse selectProfile(@PathVariable String email){
        return userService.fetchUserByEmail(email);
    }

    @ApiOperation("Update user profile")
    @PutMapping("/user")
    public UserProfileResponse updateProfileAsUser(@Valid @RequestBody UserUpdateProfileRequest userUpdateProfileRequest, BindingResult result,  HttpServletRequest servletRequest){
        String token = servletRequest.getHeader("Authorization");
        String email = JwtUtil.fetchEmail(token);

        handleBindingResult(result);

        return userService.updateUserDetails(email, userUpdateProfileRequest);
    }

    @ApiOperation("Change password as user.")
    @PutMapping("/user/password")
    public UserProfileResponse changePasswordAsUser(@Valid @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest, BindingResult result,  HttpServletRequest servletRequest){
        String token = servletRequest.getHeader("Authorization");
        String email = JwtUtil.fetchEmail(token);

        handleBindingResult(result);

        return userService.changePassword(email, userUpdatePasswordRequest);
    }

    private void handleBindingResult(BindingResult result){
        if(result.hasErrors()) {
            throw new UserInvalidFieldException(
                    result
                            .getFieldErrors()
                            .stream()
                            .map(f -> f.getField() + ": " + f.getDefaultMessage())
                            .reduce((a, b) -> a + ", " + b)
                            .toString());
        }
    }
}
