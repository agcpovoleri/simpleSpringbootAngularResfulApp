package com.example.forumapp.exception;


public class UserNotActiveException extends BusinessValidationException {

    public UserNotActiveException() {
        super("user.not.active.exception");
    }

}