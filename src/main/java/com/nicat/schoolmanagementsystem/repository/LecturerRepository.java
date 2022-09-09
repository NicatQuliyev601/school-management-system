package com.nicat.schoolmanagementsystem.repository;

import com.nicat.schoolmanagementsystem.entity.Lecturer;
import com.nicat.schoolmanagementsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer,Long> {
    List<Lecturer> findAllByStudent(Student student);
}
