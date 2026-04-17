package com.example.schedule_app.schedule.dto;

import com.example.schedule_app.user.entity.User;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {

    private String title;
    private String content;
    private Long userId;

}
