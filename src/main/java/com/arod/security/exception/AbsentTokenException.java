package com.arod.security.exception;

public class AbsentTokenException extends RuntimeException {
    public AbsentTokenException() {
    }

    public AbsentTokenException(String message) {
        super(message);
    }

    public AbsentTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbsentTokenException(Throwable cause) {
        super(cause);
    }

    public AbsentTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
