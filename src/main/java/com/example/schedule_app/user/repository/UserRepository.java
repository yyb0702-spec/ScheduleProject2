package com.example.schedule_app.user.repository;

import com.example.schedule_app.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(@Email @NotBlank String email);//select * from user where email = ?
}
