package com.reksio.restbackend.exception.advertisement;

public class AdvertisementFailedDeleteExcetion extends RuntimeException {
    public AdvertisementFailedDeleteExcetion() {
        super();
    }

    public AdvertisementFailedDeleteExcetion(String message) {
        super(message);
    }

    public AdvertisementFailedDeleteExcetion(String message, Throwable cause) {
        super(message, cause);
    }
}
