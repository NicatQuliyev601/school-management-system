package com.nicat.schoolmanagementsystem.controller;

import com.nicat.schoolmanagementsystem.request.GroupRequest;
import com.nicat.schoolmanagementsystem.request.ScheduleRequest;
import com.nicat.schoolmanagementsystem.response.GroupResponse;
import com.nicat.schoolmanagementsystem.response.ScheduleResponse;
import com.nicat.schoolmanagementsystem.service.GroupService;
import com.nicat.schoolmanagementsystem.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> findAll() {
        return ResponseEntity.ok(scheduleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(scheduleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ScheduleRequest> create(@RequestBody ScheduleRequest scheduleRequest) {
        return ResponseEntity.ok(scheduleService.save(scheduleRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleRequest> update(@PathVariable("id") Long id, @RequestBody ScheduleRequest scheduleRequest) {
        return ResponseEntity.ok(scheduleService.update(scheduleRequest, id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        scheduleService.delete(id);
    }
}
