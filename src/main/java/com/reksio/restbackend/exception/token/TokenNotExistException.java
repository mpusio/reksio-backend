package com.reksio.restbackend.exception.token;

public class TokenNotExistException extends RuntimeException{
    public TokenNotExistException() { super();}

    public TokenNotExistException(String message) {
        super(message);
    }

    public TokenNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
