package com.nicat.schoolmanagementsystem.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleRequest {
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long studentId;

    private Long lecturerId;
}
