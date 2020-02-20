package com.reksio.restbackend.exception.user;

public class UserNotExistException extends RuntimeException {

    public UserNotExistException(String email) { super("Cannot find user with email " + email); }

    public UserNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
