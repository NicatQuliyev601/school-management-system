package com.nicat.schoolmanagementsystem.response;

import lombok.Data;

import java.util.List;

@Data
public class LecturerResponse {
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String number;

    private List<ScheduleResponse> schedules;

    private List<GroupResponse> groups;

    private List<LessonResponse> lessons;
}
