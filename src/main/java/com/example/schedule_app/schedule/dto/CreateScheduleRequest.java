package com.example.schedule_app.schedule.dto;

import com.example.schedule_app.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {

    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    @Size(min = 10, max= 255)
    private String content;

}
