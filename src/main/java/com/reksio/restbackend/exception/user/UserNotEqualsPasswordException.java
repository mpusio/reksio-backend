package com.reksio.restbackend.exception.user;

public class UserNotEqualsPasswordException extends RuntimeException {

    public UserNotEqualsPasswordException() {
    }

    public UserNotEqualsPasswordException(String message) {
        super(message);
    }

    public UserNotEqualsPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
