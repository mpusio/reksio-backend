package com.reksio.restbackend.exception.payment;

public class ChargeFailedException extends RuntimeException {
    public ChargeFailedException() {
        super();
    }

    public ChargeFailedException(String message) {
        super(message);
    }

    public ChargeFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
