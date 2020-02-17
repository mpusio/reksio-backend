package com.reksio.restbackend.exception.advertisement;

public class AdvertisementInvalidFieldException extends RuntimeException{
    public AdvertisementInvalidFieldException() {
        super();
    }

    public AdvertisementInvalidFieldException(String message) {
        super(message);
    }

    public AdvertisementInvalidFieldException(String message, Throwable cause) {
        super(message, cause);
    }
}
