package com.example.forumapp.exception;

public class UsernameAlreadyUsedException extends BusinessValidationException {

    public UsernameAlreadyUsedException() {
        super("username.already.used.exception");
    }

}
