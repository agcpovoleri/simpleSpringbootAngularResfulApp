package com.example.forumapp.exception;

public class InvalidPasswordException extends BusinessValidationException{

    public InvalidPasswordException() {
        super("login.request.invalid.password.exception");
    }
}
