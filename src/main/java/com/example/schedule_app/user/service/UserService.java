package com.example.schedule_app.user.service;

import com.example.schedule_app.user.dto.CreateUserRequest;
import com.example.schedule_app.user.dto.CreateUserResponse;
import com.example.schedule_app.user.dto.GetOneUserResponse;
import com.example.schedule_app.user.entity.User;
import com.example.schedule_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //────────────────────────────────────생성────────────────────────────────────
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
    //────────────────────────────────────단건조회────────────────────────────────────
    @Transactional(readOnly = true)
    public GetOneUserResponse getOne(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        return new GetOneUserResponse(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt());
    }
    //────────────────────────────────────전체조회────────────────────────────────────
    @Transactional(readOnly = true)
    public List<GetOneUserResponse> getAll() {
            List<User> users = userRepository.findAll();

            return users.stream()
                    .map(user -> new GetOneUserResponse(
                            user.getId(),
                            user.getName(),
                            user.getEmail(),
                            user.getCreatedAt(),
                            user.getModifiedAt()
                    ))
                    .toList();
        }
}
