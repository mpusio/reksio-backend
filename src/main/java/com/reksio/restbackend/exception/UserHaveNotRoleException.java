package com.reksio.restbackend.exception;

public class UserHaveNotRoleException extends RuntimeException {
    public UserHaveNotRoleException() {
        super();
    }

    public UserHaveNotRoleException(String message) {
        super(message);
    }
}