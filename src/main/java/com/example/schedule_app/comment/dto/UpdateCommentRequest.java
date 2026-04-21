package com.example.schedule_app.comment.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class UpdateCommentRequest {

    @NotBlank
    @Size(min = 10, max= 255)
    private String content;
}
