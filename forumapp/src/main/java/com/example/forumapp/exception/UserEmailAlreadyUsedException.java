package com.example.forumapp.exception;

public class UserEmailAlreadyUsedException extends BusinessValidationException {

    public UserEmailAlreadyUsedException() {
        super("user.email.already.used.exception");
    }

}
