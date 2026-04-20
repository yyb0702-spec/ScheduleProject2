package com.example.schedule_app.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequest {

    @NotBlank(message= "이름을 입력해주세요!")
    private String name;
    @NotBlank(message= "이메일 입력해주세요!")
    @Email(message= "올바른 이메일 형식이 아닙니다")
    private String email;
    @NotBlank(message= "비밀번호는 필수입니다")
    @Size(min = 8, message = "비밀번호는 8자 이상이여야합니다")
    private String password;
}
