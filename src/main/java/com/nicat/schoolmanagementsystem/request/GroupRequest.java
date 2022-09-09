package com.nicat.schoolmanagementsystem.request;

import lombok.Data;

@Data
public class GroupRequest {
    private Long id;

    private String name;

    private String faculty;

    private Long studentId;

    private Long lecturerId;
}
