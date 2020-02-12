package com.reksio.restbackend.registration;

import com.reksio.restbackend.exception.UserExistException;
import com.reksio.restbackend.exception.UserInvalidFormException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserRegistrationAdvice {
    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String userExistAdvice(UserExistException ex) {
        return ex.getMessage()+ " Try pass another email.";
    }

    @ExceptionHandler(UserInvalidFormException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String userInvalidParametersAdvice(UserInvalidFormException ex) {
        return ex.getMessage();
    }
}