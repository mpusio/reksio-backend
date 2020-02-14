package com.reksio.restbackend.registration;

import com.reksio.restbackend.exception.ExceptionResponse;
import com.reksio.restbackend.exception.user.UserExistException;
import com.reksio.restbackend.exception.user.UserInvalidFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class UserRegistrationAdvice {

    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse userExistAdvice(UserExistException ex, WebRequest request){
        return ExceptionResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .solution("Pass different email.")
                .build();
    }

    @ExceptionHandler(UserInvalidFieldException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ExceptionResponse userInvalidParametersAdvice(UserInvalidFieldException ex, WebRequest request) {
        return ExceptionResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.NOT_ACCEPTABLE.value())
                .error(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .solution("Pass correct data.")
                .build();
    }
}