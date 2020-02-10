package com.reksio.restbackend.exception;

public class UserIsNotActiveException extends RuntimeException {
    public UserIsNotActiveException() {
        super();
    }

    public UserIsNotActiveException(String message) {
        super(message);
    }
}
