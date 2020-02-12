package com.reksio.restbackend.exception;

public class UserInvalidFormException extends RuntimeException {

    public UserInvalidFormException() {
        super();
    }

    public UserInvalidFormException(String message) {
        super(message);
    }

    public UserInvalidFormException(String message, Throwable cause) {
        super(message, cause);
    }
}
