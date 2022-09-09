package com.nicat.schoolmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "lessons")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lessonName;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private Student student;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private Lecturer lecturer;

}
