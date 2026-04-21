package com.example.schedule_app.schedule.dto;


import java.time.LocalDateTime;

public record GetAllScheduleResponse(Long id, String title, String content, String name, LocalDateTime createdAt,
    LocalDateTime modifiedAt) {

    }

