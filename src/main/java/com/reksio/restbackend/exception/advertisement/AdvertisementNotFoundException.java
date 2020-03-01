package com.reksio.restbackend.exception.advertisement;

public class AdvertisementNotFoundException extends RuntimeException {
    public AdvertisementNotFoundException() {
        super();
    }

    public AdvertisementNotFoundException(String message) {
        super(message);
    }

    public AdvertisementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
