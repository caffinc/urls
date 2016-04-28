package com.caffinc.urls.core.exceptions;

/**
 * @author Sriram
 * @since 4/26/2016
 */
public class UIOException extends Exception {
    public UIOException() {
    }

    public UIOException(String message) {
        super(message);
    }

    public UIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public UIOException(Throwable cause) {
        super(cause);
    }

    public UIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
