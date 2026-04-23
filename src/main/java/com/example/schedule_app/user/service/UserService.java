package com.example.schedule_app.user.service;

import com.example.schedule_app.common.exception.EmailException;
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
    private final PasswordEncoder passwordEncoder; //비밀번호 암호화

    //────────────────────────────────────생성────────────────────────────────────
    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {

        duplicationEmail(request.getEmail());

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

        User user = findUserById(userId);
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
        User user = findUserById(userId);
        duplicationEmailUpdate(user, request.getEmail());
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
        User user = findUserById(userId);

        userRepository.delete(user);
    }


    private User findUserById(Long userId)
    {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다"));
    }

    private void duplicationEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailException("이미 존재하는 이메일입니다.");
        }
    }

    private void duplicationEmailUpdate(User user, String email) {
        if (!user.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            throw new EmailException("이미 존재하는 이메일입니다.");
        }
    }
}
