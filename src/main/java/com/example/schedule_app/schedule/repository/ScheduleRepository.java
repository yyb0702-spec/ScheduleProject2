package com.example.schedule_app.schedule.repository;

import com.example.schedule_app.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule , Long> {

    Optional<Schedule> findByIdAndUserId(Long scheduleId, Long userId);
    Page<Schedule> findAllByUserId(Long userId, Pageable pageable);
}
