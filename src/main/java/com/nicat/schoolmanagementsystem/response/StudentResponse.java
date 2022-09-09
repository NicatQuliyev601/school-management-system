package com.nicat.schoolmanagementsystem.response;

import lombok.Data;

import java.util.List;

@Data
public class StudentResponse {
    private Long id;

    private String studentNumber;

    private String name;

    private String surname;

    private String studentEmail;

    private String age;

    private List<LessonResponse> lessons;

    private List<GroupResponse> groups;

    private List<ScheduleResponse> schedules;

    private List<LecturerResponse> lecturers;
}
