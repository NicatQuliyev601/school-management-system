package com.nicat.schoolmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentNumber;

    private String name;

    private String surname;

    private String studentEmail;

    private String age;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    @Fetch(FetchMode.JOIN)
    private List<Lesson> lessons = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    @Fetch(FetchMode.JOIN)
    private List<Group> groups = new ArrayList<>();
    
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    @Fetch(FetchMode.JOIN)
    private List<Schedule> schedules = new ArrayList<>();
    
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    @Fetch(FetchMode.JOIN)
    private List<Lecturer> lecturers = new ArrayList<>();



}
