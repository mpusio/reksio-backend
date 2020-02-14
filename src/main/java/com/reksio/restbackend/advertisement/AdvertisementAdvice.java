package com.reksio.restbackend.advertisement;

import com.reksio.restbackend.exception.ExceptionResponse;
import com.reksio.restbackend.exception.advertisement.AdvertisementInvalidFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class AdvertisementAdvice {

    @ExceptionHandler(AdvertisementInvalidFieldException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ExceptionResponse userInvalidParametersAdvice(AdvertisementInvalidFieldException ex, WebRequest request) {
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
