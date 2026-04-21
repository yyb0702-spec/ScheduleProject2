package com.example.schedule_app.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {

    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    @Size(min = 10, max= 255 , message = "10 자이상 255 자 이하로 입력해주세요")
    private String content;
}
