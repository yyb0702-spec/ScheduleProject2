package com.example.schedule_app.schedule.service;

import com.example.schedule_app.schedule.dto.CreateScheduleRequest;
import com.example.schedule_app.schedule.dto.CreateScheduleResponse;
import com.example.schedule_app.schedule.entity.Schedule;
import com.example.schedule_app.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;


    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        Schedule schedule = new Schedule(request.getName(),request.getTitle(),request.getContent());
        Schedule saveSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(saveSchedule.getId(),
                saveSchedule.getName(),
                saveSchedule.getTitle(),
                saveSchedule.getContent(),
                saveSchedule.getCreatedAt(),
                saveSchedule.getModifiedAt());
    }
}
