package com.reksio.restbackend.exception.filter;

public class FilterNotFoundException extends RuntimeException {
    public FilterNotFoundException() {
        super();
    }

    public FilterNotFoundException(String message) {
        super(message);
    }

    public FilterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
