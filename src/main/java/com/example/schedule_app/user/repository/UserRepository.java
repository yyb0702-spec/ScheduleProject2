package com.example.schedule_app.user.repository;

import com.example.schedule_app.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
