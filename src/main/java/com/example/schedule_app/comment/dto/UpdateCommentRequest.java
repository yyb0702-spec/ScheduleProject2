package com.example.schedule_app.comment.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class UpdateCommentRequest {

    @NotBlank
    @Length(max = 100)
    private String content;
}
