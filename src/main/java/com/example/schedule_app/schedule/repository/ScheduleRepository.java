package com.example.schedule_app.schedule.repository;

import com.example.schedule_app.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule , Long> {

}
