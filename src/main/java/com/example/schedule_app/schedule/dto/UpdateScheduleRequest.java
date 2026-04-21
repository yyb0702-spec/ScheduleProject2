package com.example.schedule_app.schedule.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {

    private String title;
    @Size(min = 10, max= 255)
    private String content;
}
