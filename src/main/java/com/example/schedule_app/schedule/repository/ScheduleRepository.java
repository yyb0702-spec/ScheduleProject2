package com.example.schedule_app.schedule.repository;

import com.example.schedule_app.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule , Long> {

    List<Schedule> findAllByUserId(Long userId);
    Optional<Schedule> findByIdAndUserId(Long scheduleId, Long userId);

}
