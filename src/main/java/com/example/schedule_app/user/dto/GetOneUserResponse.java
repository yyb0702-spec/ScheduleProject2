package com.example.schedule_app.user.dto;

import java.time.LocalDateTime;

public record GetOneUserResponse(Long id, String name, String email, LocalDateTime createdAt,
                                 LocalDateTime modifiedAt) {

}
