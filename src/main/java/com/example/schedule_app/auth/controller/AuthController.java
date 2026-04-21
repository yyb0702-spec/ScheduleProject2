package com.example.schedule_app.auth.controller;

import com.example.schedule_app.auth.dto.LoginRequest;
import com.example.schedule_app.auth.dto.LoginResponse;
import com.example.schedule_app.auth.dto.SessionUser;
import com.example.schedule_app.auth.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpSession session)
    {
        LoginResponse result = authService.login(request);

        session.setAttribute("loginUser",
                new SessionUser(result.id(), result.email()));

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@SessionAttribute(name="loginUser", required = false)SessionUser sessionUser, HttpSession session)
    {
        if(sessionUser == null){
            return ResponseEntity.badRequest().build();
        }
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
