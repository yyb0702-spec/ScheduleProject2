package com.example.schedule_app.schedule.service;

import com.example.schedule_app.auth.dto.SessionUser;
import com.example.schedule_app.comment.dto.GetCommentResponse;
import com.example.schedule_app.comment.entity.Comment;
import com.example.schedule_app.comment.repository.CommentRepository;
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
    private final CommentRepository commentRepository;

    //────────────────────────────────────생성────────────────────────────────────
    @Transactional
    public CreateScheduleResponse save(SessionUser sessionUser, CreateScheduleRequest request) {
        User user = findUserById(sessionUser.id());

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
        Schedule schedule = findOwnerSchedule(scheduleId,sessionUser.id());

        List<Comment> comments = commentRepository.findAllByScheduleId(scheduleId);

        List<GetCommentResponse> dtos = comments.stream()
                .map(comment -> new GetCommentResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                ))
                .toList();

        return new GetOneScheduleResponse(schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                dtos);
    }

    //────────────────────────────────────조회────────────────────────────────────
    @Transactional(readOnly = true)
    public List<GetAllScheduleResponse> getAll(SessionUser sessionUser) {

        List<Schedule> schedules = scheduleRepository.findAllByUserId(sessionUser.id());

        return schedules.stream()
                .map(schedule -> new GetAllScheduleResponse(
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

        Schedule schedule = findOwnerSchedule(scheduleId,sessionUser.id());

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

        Schedule schedule = findOwnerSchedule(scheduleId,sessionUser.id());

        commentRepository.deleteAllByScheduleId(scheduleId);//댓글 먼저삭제
        scheduleRepository.deleteById(scheduleId);
    }

    private User findUserById(Long userId)
    {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다."));
    }
    private Schedule findOwnerSchedule(Long scheduleId, Long userId) {
        return scheduleRepository.findByIdAndUserId(scheduleId, userId)
                .orElseThrow(() -> new ScheduleNotFoundException("일정을 찾을 수 없습니다."));
    }
}
