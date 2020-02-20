package com.reksio.restbackend.exception.user;

public class UserNotEqualsPasswordException extends RuntimeException {
    public UserNotEqualsPasswordException() {
        super();
    }

    public UserNotEqualsPasswordException(String message) {
        super(message);
    }

    public UserNotEqualsPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
