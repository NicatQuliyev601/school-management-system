package com.nicat.schoolmanagementsystem.request;


import lombok.Data;

@Data
public class LessonRequest {
    private Long id;

    private String lessonName;

    private Long studentId;

    private Long lecturerId;
}
