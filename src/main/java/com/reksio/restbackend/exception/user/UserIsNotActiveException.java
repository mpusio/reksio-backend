package com.reksio.restbackend.exception.user;

public class UserIsNotActiveException extends RuntimeException {
    public UserIsNotActiveException() {
        super();
    }

    public UserIsNotActiveException(String message) {
        super(message);
    }
}
