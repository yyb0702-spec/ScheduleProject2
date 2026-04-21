package com.example.schedule_app.common.exception;

import org.springframework.http.HttpStatus;

public class ScheduleNotFoundException extends ServiceException{

    public ScheduleNotFoundException(String message)
    {
        super(HttpStatus.NOT_FOUND, message);
        ////404 403 통일 에러처리
    }
}
