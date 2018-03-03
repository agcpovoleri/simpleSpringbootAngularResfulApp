package com.example.forumapp.dto;

import org.springframework.http.HttpStatus;

public class GenericErrorResponse {

    private String errorStatus;
    private String errorMessage;

    public GenericErrorResponse(String errorMessage, HttpStatus errorStatus) {
        this.errorMessage = errorMessage;
        this.errorStatus = errorStatus.toString();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }
}