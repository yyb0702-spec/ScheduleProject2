package com.example.schedule_app.user.service;

import com.example.schedule_app.common.exception.UserNotFoundException;
import com.example.schedule_app.config.PasswordEncoder;
import com.example.schedule_app.user.dto.*;
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
    private final PasswordEncoder passwordEncoder;

    //────────────────────────────────────생성────────────────────────────────────
    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {

        String encodePassword = passwordEncoder.encode(request.getPassword());

        User user = new User(request.getName(), request.getEmail(),encodePassword);
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
                () -> new UserNotFoundException("유저가 존재하지 않습니다."));
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
    //────────────────────────────────────수정────────────────────────────────────
    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("유저가 존재하지 않습니다."));
        user.userUpdate(request.getName(),request.getEmail());
        return new UpdateUserResponse(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt());
    }
    //────────────────────────────────────삭제────────────────────────────────────
    @Transactional
    public void delete(Long userId) {
        boolean existence = userRepository.existsById(userId);

        if (!existence) {
            throw new UserNotFoundException("없는 유저입니다.");
        }

        userRepository.deleteById(userId);
    }
}
