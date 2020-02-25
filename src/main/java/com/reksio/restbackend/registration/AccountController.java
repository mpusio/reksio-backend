package com.reksio.restbackend.registration;

import com.reksio.restbackend.collection.user.User;
import com.reksio.restbackend.exception.user.UserInvalidFieldException;
import com.reksio.restbackend.security.LoginData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Api("Provide operations like register, restore password, login, logout.")
public class AccountController {

    private final RegistrationService registrationService;

    public AccountController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @ApiOperation("Registration.")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@Valid @RequestBody RegistrationRequest request, BindingResult result){

        if(result.hasErrors()){
            throw new UserInvalidFieldException(
                    result
                        .getFieldErrors()
                        .stream()
                        .map(f -> f.getField() + ": " + f.getDefaultMessage())
                        .reduce((a, b) -> a + b)
                        .toString());
        }

        return registrationService.registerUser(request);
    }

    @ApiOperation("Login.")
    @PostMapping("/login")
    public void login(@RequestBody LoginData loginData) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }

    //TODO logout
//    @ApiOperation("Logout.")
//    @PostMapping("/logout")
//    public void logout() {
//        throw new IllegalStateException("This method shouldn't be called. It's not implemented yet.");
//    }
}
