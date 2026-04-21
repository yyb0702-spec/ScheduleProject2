package com.example.schedule_app.schedule.controller;

import com.example.schedule_app.auth.dto.SessionUser;
import com.example.schedule_app.schedule.dto.*;
import com.example.schedule_app.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    //────────────────────────────────────생성────────────────────────────────────
    @PostMapping
    public ResponseEntity<CreateScheduleResponse> createSchedule(@SessionAttribute(name = "loginUser") SessionUser sessionUser, @RequestBody CreateScheduleRequest request) {
        CreateScheduleResponse result = scheduleService.save(sessionUser, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    //────────────────────────────────────단건조회────────────────────────────────────
    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetOneScheduleResponse> getOneSchedule(@SessionAttribute(name = "loginUser") SessionUser sessionUser, @PathVariable Long scheduleId) {
        GetOneScheduleResponse result = scheduleService.getOne(sessionUser, scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //────────────────────────────────────전체조회────────────────────────────────────
    // 페이지네이션 (page=0부터 시작, size 기본 10)
    @GetMapping
    public ResponseEntity<Page<GetAllScheduleResponse>> getAllSchedule(@SessionAttribute(name = "loginUser") SessionUser sessionUser,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size) {
        Page<GetAllScheduleResponse> result = scheduleService.getAll(sessionUser, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //────────────────────────────────────수정────────────────────────────────────
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(@SessionAttribute(name = "loginUser") SessionUser sessionUser, @Valid @PathVariable Long scheduleId, @RequestBody UpdateScheduleRequest request) {
        UpdateScheduleResponse result = scheduleService.updateSchedule(sessionUser, scheduleId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //────────────────────────────────────삭제────────────────────────────────────
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@SessionAttribute(name = "loginUser") SessionUser sessionUser, @PathVariable Long scheduleId) {
        scheduleService.delete(sessionUser, scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
