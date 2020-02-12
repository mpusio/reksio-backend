package com.reksio.restbackend.registration;

import com.reksio.restbackend.collection.user.User;
import com.reksio.restbackend.exception.UserInvalidFormException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public User register(@Valid @RequestBody RegistrationRequest request, BindingResult result){

        if(result.hasErrors()){
            throw new UserInvalidFormException(
                    result
                        .getFieldErrors()
                        .stream()
                        .map(f -> f.getField() + ": " + f.getDefaultMessage())
                        .reduce((a, b) -> a + b)
                        .toString());
        }

        return registrationService.registerUser(request);
    }
}
