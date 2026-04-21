package com.example.schedule_app.common.exception;

import org.springframework.http.HttpStatus;

public class NotOwnerException extends ServiceException {

    public NotOwnerException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}

