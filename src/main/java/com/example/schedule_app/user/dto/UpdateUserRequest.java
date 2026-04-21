package com.example.schedule_app.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateUserRequest {

    @NotBlank(message ="이름을 입력해주세요")
    private String name;
    @Email(message ="이메일 형식에 맞게 입력해주세요")
    private String email;
}
