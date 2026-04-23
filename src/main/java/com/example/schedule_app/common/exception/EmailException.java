package com.example.schedule_app.common.exception;

import org.springframework.http.HttpStatus;

public class EmailException extends ServiceException {

    public EmailException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
