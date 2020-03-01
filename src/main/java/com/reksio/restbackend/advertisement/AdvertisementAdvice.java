package com.reksio.restbackend.advertisement;

import com.reksio.restbackend.exception.ExceptionResponse;
import com.reksio.restbackend.exception.advertisement.AdvertisementFailedDeleteExcetion;
import com.reksio.restbackend.exception.advertisement.AdvertisementInvalidFieldException;
import com.reksio.restbackend.exception.advertisement.AdvertisementNotFoundException;
import com.reksio.restbackend.exception.filter.FilterNotFoundException;
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

    @ExceptionHandler(AdvertisementFailedDeleteExcetion.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse advertisementFailedDeleteAdvice(AdvertisementFailedDeleteExcetion ex, WebRequest request) {
        return ExceptionResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .solution("Do not try delete foreign advertisement, check uuid code.")
                .build();
    }

    @ExceptionHandler(AdvertisementNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse advertisementNotFoundAdvice(AdvertisementNotFoundException ex, WebRequest request) {
        return ExceptionResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .solution("Check uuid code is valid.")
                .build();
    }

    @ExceptionHandler(FilterNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse filterNotFoundAdvice(FilterNotFoundException ex, WebRequest request) {
        return ExceptionResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .solution("Pass corrects param (names like class filed), or remove filter. Also filter may be not implemented yet.")
                .build();
    }
}
