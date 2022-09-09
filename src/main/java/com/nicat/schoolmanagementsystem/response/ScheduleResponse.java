package com.nicat.schoolmanagementsystem.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleResponse {
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
