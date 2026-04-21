package com.example.schedule_app.comment.dto;

import java.time.LocalDateTime;

public record GetCommentResponse(Long id, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {

}
