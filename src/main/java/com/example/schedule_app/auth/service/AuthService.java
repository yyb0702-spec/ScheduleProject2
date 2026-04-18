package com.example.schedule_app.auth.service;

import com.example.schedule_app.auth.dto.LoginRequest;
import com.example.schedule_app.auth.dto.LoginResponse;
import com.example.schedule_app.user.entity.User;
import com.example.schedule_app.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

@Transactional(readOnly = true)
    public LoginResponse login(@Valid LoginRequest request) {

    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));

    if (!request.getPassword().equals(user.getPassword())) {
        throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
    }
    return new LoginResponse(user.getId(),
            user.getName(),
            user.getEmail());
    }
}
