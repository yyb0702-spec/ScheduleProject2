package com.example.schedule_app.common.exception;

import org.springframework.http.HttpStatus;

public class LoginException extends ServiceException{

    public LoginException(String message)
    {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
