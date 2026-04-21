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

    //────────────────────────────────────로그인────────────────────────────────────
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpSession session)
    {
        LoginResponse result = authService.login(request);

        session.setAttribute("loginUser",
                new SessionUser(result.id(), result.email())); // 입력한 세션 저장

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //────────────────────────────────────로그아웃────────────────────────────────────
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@SessionAttribute(name="loginUser", required = false)SessionUser sessionUser, HttpSession session)
    {
        if(sessionUser == null){ //세션없는상태에선 에러메세지
            return ResponseEntity.badRequest().build();
        }
        session.invalidate(); // 세션 삭제 , 로그인 해제
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
