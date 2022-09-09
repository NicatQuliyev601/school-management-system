package com.nicat.schoolmanagementsystem.request;

import lombok.Data;

@Data
public class StudentRequest {
    private Long id;

    private String studentNumber;

    private String name;

    private String surname;

    private String studentEmail;

    private String age;
}
