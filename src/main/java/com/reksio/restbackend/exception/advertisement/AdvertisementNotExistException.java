package com.reksio.restbackend.exception.advertisement;

public class AdvertisementNotExistException extends RuntimeException {
    public AdvertisementNotExistException() {
        super();
    }

    public AdvertisementNotExistException(String message) {
        super(message);
    }

    public AdvertisementNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
