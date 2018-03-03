package com.example.forumapp.exception;

public class UserAlreadyExistException extends BusinessValidationException {

    public UserAlreadyExistException() {
        super("user.already.exist.with.username.exception");
    }

}
