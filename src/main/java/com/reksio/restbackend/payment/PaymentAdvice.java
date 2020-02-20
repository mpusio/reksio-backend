package com.reksio.restbackend.payment;

import com.reksio.restbackend.exception.ExceptionResponse;
import com.reksio.restbackend.exception.payment.ChargeFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class PaymentAdvice {

    @ExceptionHandler(ChargeFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse userInvalidParametersAdvice(ChargeFailedException ex, WebRequest request) {
        return ExceptionResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .solution("Pass unused and correct payment token.")
                .build();
    }
}
