package com.example.schedule_app.schedule.controller;

import com.example.schedule_app.auth.dto.SessionUser;
import com.example.schedule_app.schedule.dto.*;
import com.example.schedule_app.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponse> createSchedule(@SessionAttribute(name = "loginUser") SessionUser sessionUser, @RequestBody CreateScheduleRequest request)
    {
        CreateScheduleResponse result = scheduleService.save(sessionUser,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetOneScheduleResponse> getOneSchedule(@SessionAttribute(name = "loginUser") SessionUser sessionUser,@PathVariable Long scheduleId)
    {
        GetOneScheduleResponse result = scheduleService.getOne(sessionUser,scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping
    public ResponseEntity<List<GetAllScheduleResponse>> getAllSchedule(@SessionAttribute(name = "loginUser") SessionUser sessionUser)
    {
        List<GetAllScheduleResponse> result = scheduleService.getAll(sessionUser);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(@SessionAttribute(name = "loginUser") SessionUser sessionUser ,@Valid @PathVariable Long scheduleId , @RequestBody UpdateScheduleRequest request)
    {
        UpdateScheduleResponse result = scheduleService.updateSchedule(sessionUser,scheduleId,request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@SessionAttribute(name = "loginUser") SessionUser sessionUser , @PathVariable Long scheduleId)
    {
        scheduleService.delete(sessionUser,scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
