package com.caffinc.urls.core.exceptions;

/**
 * @author Sriram
 * @since 4/27/2016
 */
public class ShorteningException extends RuntimeException {
    public ShorteningException() {
    }

    public ShorteningException(String message) {
        super(message);
    }

    public ShorteningException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShorteningException(Throwable cause) {
        super(cause);
    }

    public ShorteningException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
