package com.reksio.restbackend.exception.filter;

public class FilterBadParameterException extends RuntimeException {
    public FilterBadParameterException() {
        super();
    }

    public FilterBadParameterException(String message) {
        super(message);
    }

    public FilterBadParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
