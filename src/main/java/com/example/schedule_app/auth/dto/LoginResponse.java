package com.example.schedule_app.auth.dto;

import lombok.Getter;

@Getter
public class LoginResponse {

    private final Long id;
    private final String name;
    private final String email;


    public LoginResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
