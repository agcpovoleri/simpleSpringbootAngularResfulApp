package com.example.forumapp.exception;

public class BusinessValidationException extends RuntimeException{

    public BusinessValidationException(final String message) {
        super(message);
    }

    public BusinessValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
