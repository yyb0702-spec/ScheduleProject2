package com.example.schedule_app.user.service;

import com.example.schedule_app.user.dto.CreateUserRequest;
import com.example.schedule_app.user.dto.CreateUserResponse;
import com.example.schedule_app.user.entity.User;
import com.example.schedule_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {

        User user = new User(request.getName(),request.getEmail());
        User savedUser = userRepository.save(user);

        return new CreateUserResponse(savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt());
    }
}
