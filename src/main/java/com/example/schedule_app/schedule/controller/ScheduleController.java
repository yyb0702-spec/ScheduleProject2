package com.example.schedule_app.schedule.controller;

import com.example.schedule_app.schedule.dto.*;
import com.example.schedule_app.schedule.service.ScheduleService;
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
    public ResponseEntity<CreateScheduleResponse> createSchedule(@RequestBody CreateScheduleRequest request)
    {
        CreateScheduleResponse result = scheduleService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetOneScheduleResponse> getOneSchedule(@PathVariable Long scheduleId)
    {
        GetOneScheduleResponse result = scheduleService.getOne(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping
    public ResponseEntity<List<GetOneScheduleResponse>> getAllSchedule()
    {
        List<GetOneScheduleResponse> result = scheduleService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(@PathVariable Long scheduleId , @RequestBody UpdateScheduleRequest request)
    {
        UpdateScheduleResponse result = scheduleService.updateSchedule(scheduleId,request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}
