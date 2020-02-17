package com.reksio.restbackend.exception.user;

public class UserInvalidFieldException extends RuntimeException {

    public UserInvalidFieldException() {
        super();
    }

    public UserInvalidFieldException(String message) {
        super(message);
    }

    public UserInvalidFieldException(String message, Throwable cause) {
        super(message, cause);
    }
}
