package com.nicat.schoolmanagementsystem.repository;

import com.nicat.schoolmanagementsystem.entity.Group;
import com.nicat.schoolmanagementsystem.entity.Lecturer;
import com.nicat.schoolmanagementsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByStudent(Student student);

    List<Group> findAllByLecturer(Lecturer lecturer);
}
