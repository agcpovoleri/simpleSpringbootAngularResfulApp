package com.example.forumapp.dto;

import com.example.forumapp.util.SecurityUtils;

import javax.validation.constraints.NotNull;

public class LoginRequest {

    @NotNull(message = "login.request.login.not.null")
    private String login;

    @NotNull(message = "login.request.authentication.password.not.null")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}