package com.reksio.restbackend.exception.post;

public class PostFailedDeleteException extends RuntimeException{
    public PostFailedDeleteException() {
    }

    public PostFailedDeleteException(String message) {
        super(message);
    }

    public PostFailedDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
