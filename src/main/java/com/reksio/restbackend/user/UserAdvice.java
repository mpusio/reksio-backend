package com.reksio.restbackend.user;

import com.reksio.restbackend.exception.ExceptionResponse;
import com.reksio.restbackend.exception.user.TokenExpiredException;
import com.reksio.restbackend.exception.user.UserInvalidFieldException;
import com.reksio.restbackend.exception.user.UserNotEqualsPasswordException;
import com.reksio.restbackend.exception.user.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class UserAdvice {

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

    @ExceptionHandler(UserNotEqualsPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse userInvalidPasswordAdvice(UserNotEqualsPasswordException ex, WebRequest request) {
        return ExceptionResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .solution("Pass correct old password.")
                .build();
    }

    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse cannotFindAdvice(UserNotExistException ex, WebRequest request) {
        return ExceptionResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .solution("Check is email correct.")
                .build();
    }

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ExceptionResponse cannotFindAdvice(TokenExpiredException ex, WebRequest request) {
        return ExceptionResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.GONE.value())
                .error(HttpStatus.GONE.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .solution("Renew token.")
                .build();
    }
}
