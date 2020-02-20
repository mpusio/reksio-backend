package com.reksio.restbackend.exception.advertisement;

public class AdvertisementOwnerException extends RuntimeException {
    public AdvertisementOwnerException() { super(); }

    public AdvertisementOwnerException(String message) {
        super(message);
    }

    public AdvertisementOwnerException(String message, Throwable cause) {
        super(message, cause);
    }
}
