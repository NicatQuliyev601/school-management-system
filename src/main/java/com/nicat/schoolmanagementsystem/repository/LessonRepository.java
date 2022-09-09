package com.nicat.schoolmanagementsystem.repository;

import com.nicat.schoolmanagementsystem.entity.Lecturer;
import com.nicat.schoolmanagementsystem.entity.Lesson;
import com.nicat.schoolmanagementsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByStudent(Student student);

    List<Lesson> findAllByLecturer(Lecturer lecturer);
}
