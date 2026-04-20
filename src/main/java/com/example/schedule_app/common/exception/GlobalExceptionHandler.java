package com.example.schedule_app.common.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleIllegalStateException(ServiceException  ex)
    {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .findFirst() // 첫 번째 에러를 Optional로 가져옴
                .map(DefaultMessageSourceResolvable::getDefaultMessage) // 있다면 메시지로 변환
                .orElse("입력 값이 잘못되었습니다."); // 없다면 기본 메시지 사용

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
