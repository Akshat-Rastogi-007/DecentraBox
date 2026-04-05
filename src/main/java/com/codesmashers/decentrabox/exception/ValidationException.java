package com.codesmashers.decentrabox.exception;

public class ValidationException extends RuntimeException {

    private String errorCode;

    public ValidationException(String message) {
        super(message);
        this.errorCode = "VALIDATION_ERROR";
    }

    public ValidationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "VALIDATION_ERROR";
    }

    public String getErrorCode() {
        return errorCode;
    }
}
