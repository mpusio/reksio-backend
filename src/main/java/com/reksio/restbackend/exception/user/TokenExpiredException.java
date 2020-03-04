package com.reksio.restbackend.exception.user;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException() {
    }

    public TokenExpiredException(String message) {
        super(message);
    }

    public TokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
