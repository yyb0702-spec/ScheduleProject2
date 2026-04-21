package com.example.schedule_app.schedule.dto;

import com.example.schedule_app.comment.dto.GetCommentResponse;

import java.time.LocalDateTime;
import java.util.List;

public record GetOneScheduleResponse(Long id, String title, String content, String name, LocalDateTime createdAt,
                                     LocalDateTime modifiedAt, List<GetCommentResponse> comments) {

}
