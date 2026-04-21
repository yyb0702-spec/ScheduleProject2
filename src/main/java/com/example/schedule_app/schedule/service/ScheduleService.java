package com.example.schedule_app.schedule.service;

import com.example.schedule_app.auth.dto.SessionUser;
import com.example.schedule_app.common.exception.NotOwnerException;
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
    public CreateScheduleResponse save(SessionUser sessionUser, CreateScheduleRequest request) {
        User user = userRepository.findById(sessionUser.id())
                .orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다."));

        Schedule schedule = new Schedule(user, request.getTitle(), request.getContent());
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
    public GetOneScheduleResponse getOne(SessionUser sessionUser , Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdAndUserId(scheduleId,sessionUser.id())
                .orElseThrow(() -> new ScheduleNotFoundException("일정을 찾을 수 없습니다."));

        if (!schedule.getUser().getId().equals(sessionUser.id())) {
            throw new NotOwnerException("본인 일정만 조회할 수 있습니다.");
        }

        return new GetOneScheduleResponse(schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());
    }

    //────────────────────────────────────조회────────────────────────────────────
    @Transactional(readOnly = true)
    public List<GetOneScheduleResponse> getAll(SessionUser sessionUser) {

        List<Schedule> schedules = scheduleRepository.findAllByUserId(sessionUser.id());

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
    public UpdateScheduleResponse updateSchedule(SessionUser sessionUser, Long scheduleId, UpdateScheduleRequest request) {

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("일정을 찾을 수 없습니다.")
        );

        if (!schedule.getUser().getId().equals(sessionUser.id())) {
            throw new NotOwnerException("본인 일정만 수정할 수 있습니다.");
        }

        schedule.updateSchedule(request.getTitle(), request.getContent());

        return new UpdateScheduleResponse(schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());
    }

    //────────────────────────────────────삭제────────────────────────────────────
    @Transactional
    public void delete(SessionUser sessionUser, Long scheduleId) {

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("일정을 찾을 수 없습니다."));

        if (!schedule.getUser().getId().equals(sessionUser.id())) {
            throw new NotOwnerException("본인 일정만 삭제할 수 있습니다.");
        }

        scheduleRepository.deleteById(scheduleId);
    }
}
