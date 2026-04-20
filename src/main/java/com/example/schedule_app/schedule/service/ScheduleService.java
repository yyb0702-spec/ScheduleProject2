package com.example.schedule_app.schedule.service;

import com.example.schedule_app.common.exception.ScheduleNotFoundException;
import com.example.schedule_app.common.exception.UserNotFoundException;
import com.example.schedule_app.schedule.dto.*;
import com.example.schedule_app.schedule.entity.Schedule;
import com.example.schedule_app.schedule.repository.ScheduleRepository;
import com.example.schedule_app.user.entity.User;
import com.example.schedule_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    //────────────────────────────────────생성────────────────────────────────────
    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다."));

        Schedule schedule = new Schedule(user,request.getTitle(),request.getContent());
        Schedule saveSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(saveSchedule.getId(),
                saveSchedule.getTitle(),
                saveSchedule.getContent(),
                saveSchedule.getUser().getName(),
                saveSchedule.getCreatedAt(),
                saveSchedule.getModifiedAt());
    }
//────────────────────────────────────단건 조회────────────────────────────────────
    @Transactional(readOnly = true)
    public GetOneScheduleResponse getOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 스케쥴입니다")
        );

        return new GetOneScheduleResponse(schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());
    }
//────────────────────────────────────조회────────────────────────────────────
    @Transactional(readOnly = true)
    public List<GetOneScheduleResponse> getAll() {

        List<Schedule> schedules = scheduleRepository.findAll();

        return schedules.stream()
                .map(schedule -> new GetOneScheduleResponse(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getUser().getName(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                ))
                .toList();
    }
    //────────────────────────────────────수정────────────────────────────────────
    @Transactional
    public UpdateScheduleResponse updateSchedule(Long scheduleId, UpdateScheduleRequest request) {

            Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                    () -> new ScheduleNotFoundException("없는 스케쥴입니다")
            );
            schedule.updateSchedule(request.getTitle(),request.getContent());

            return new UpdateScheduleResponse(schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getUser().getName(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt());
    }
    //────────────────────────────────────삭제────────────────────────────────────
    @Transactional
    public void delete(Long scheduleId) {
        boolean existence = scheduleRepository.existsById(scheduleId);

        if (!existence) {
            throw new ScheduleNotFoundException("없는 스케쥴입니다.");
        }

        scheduleRepository.deleteById(scheduleId);
    }
}
