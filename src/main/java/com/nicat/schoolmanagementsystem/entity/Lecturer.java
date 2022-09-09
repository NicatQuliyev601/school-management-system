package com.nicat.schoolmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lecturers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String number;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private Student student;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "lecturer_id")
    @JsonIgnore
    @Fetch(FetchMode.JOIN)
    private List<Schedule>  schedules = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "lecturer_id")
    @JsonIgnore
    @Fetch(FetchMode.JOIN)
    private List<Group> groups = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_id")
    @JsonIgnore
    @Fetch(FetchMode.JOIN)
    private List<Lesson> lessons = new ArrayList<>();
}
