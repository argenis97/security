package com.arod.security.exception;

public class RefreshTokenNotFound extends RuntimeException {
    public RefreshTokenNotFound() {
    }

    public RefreshTokenNotFound(String message) {
        super(message);
    }

    public RefreshTokenNotFound(Throwable cause) {
        super(cause);
    }

    public RefreshTokenNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RefreshTokenNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
