package com.reksio.restbackend.exception.user;

public class UserHaveNotRoleException extends RuntimeException {
    public UserHaveNotRoleException() {
        super();
    }

    public UserHaveNotRoleException(String message) {
        super(message);
    }

    public UserHaveNotRoleException(String message, Throwable cause) {
        super(message, cause);
    }
}