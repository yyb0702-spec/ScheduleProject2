package com.example.schedule_app.common.exception;

import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends ServiceException{

    public CommentNotFoundException(String message)
    {
        super(HttpStatus.NOT_FOUND, message);
    }//404 403 통일 에러처리
}
