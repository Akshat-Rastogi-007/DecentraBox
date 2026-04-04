package com.codesmashers.decentrabox.exception;

public class UserAuthenticationError extends RuntimeException {

    public UserAuthenticationError(String message) {
        super(message);
    }

    public UserAuthenticationError(String message, Throwable cause) {
        super(message, cause);
    }
}
