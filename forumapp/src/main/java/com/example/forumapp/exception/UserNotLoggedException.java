package com.example.forumapp.exception;

public class UserNotLoggedException extends RuntimeException {

    public UserNotLoggedException() {
        super("user.not.logged.exception");
    }
}
