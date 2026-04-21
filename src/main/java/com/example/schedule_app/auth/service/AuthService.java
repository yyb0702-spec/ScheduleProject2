package com.example.schedule_app.auth.service;

import com.example.schedule_app.auth.dto.LoginRequest;
import com.example.schedule_app.auth.dto.LoginResponse;
import com.example.schedule_app.common.exception.LoginException;
import com.example.schedule_app.config.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    //────────────────────────────────────로그인────────────────────────────────────
    @Transactional(readOnly = true)
    public LoginResponse login(@Valid LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new LoginException("이메일 또는 비밀번호가 일치하지 않습니다."));

        //암호화된 DB속 비밀번호와 입력값 비교
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) { //equals 아닌 matches 로 비교
            throw new LoginException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
        return new LoginResponse(user.getId(),
                user.getName(),
                user.getEmail());
    }
}
