package com.example.schedule_app.user.controller;

import com.example.schedule_app.user.dto.*;
import com.example.schedule_app.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.annotation.JsonValueInstantiator;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request)
    {
        CreateUserResponse result = userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<GetOneUserResponse> getOneUser(@PathVariable Long userId)
    {
        GetOneUserResponse result = userService.getOne(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping
    public ResponseEntity<List<GetOneUserResponse>> getAllUser()
    {
        List<GetOneUserResponse> result = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable Long userId ,@RequestBody UpdateUserRequest request)
    {
        UpdateUserResponse result = userService.update(userId,request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId)
    {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
