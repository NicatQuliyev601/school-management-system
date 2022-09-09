package com.nicat.schoolmanagementsystem.request;

import lombok.Data;


@Data
public class LecturerRequest {
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String number;

    private Long studentId;
}
