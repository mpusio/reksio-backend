package com.reksio.restbackend.exception;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ExceptionResponse {
    private final Date timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final String solution;
}
