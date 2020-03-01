package com.reksio.restbackend.blog;

import com.reksio.restbackend.exception.ExceptionResponse;
import com.reksio.restbackend.exception.post.PostFailedDeleteException;
import com.reksio.restbackend.exception.post.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class BlogAdvice {
    @ExceptionHandler(PostFailedDeleteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse userFailedDeleteAdvice(PostFailedDeleteException ex, WebRequest request) {
        return ExceptionResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .solution("Do not delete foreign post, check uuid code.")
                .build();
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse userFailedUpdateAdvice(PostNotFoundException ex, WebRequest request) {
        return ExceptionResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .solution("Check uuid code is valid.")
                .build();
    }
}
