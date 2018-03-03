package com.example.forumapp.exception;

public class UserEmailCannotBeChangedException extends BusinessValidationException {

    public UserEmailCannotBeChangedException() {
        super("user.email.cannot.be.changed.exception");
    }

}
