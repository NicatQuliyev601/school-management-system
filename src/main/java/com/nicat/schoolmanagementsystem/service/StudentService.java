package com.nicat.schoolmanagementsystem.service;

import com.nicat.schoolmanagementsystem.entity.Student;
import com.nicat.schoolmanagementsystem.exception.NotFoundException;
import com.nicat.schoolmanagementsystem.repository.*;
import com.nicat.schoolmanagementsystem.request.StudentRequest;
import com.nicat.schoolmanagementsystem.response.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final LessonRepository lessonRepository;
    private final LecturerRepository lecturerRepository;
    private final ScheduleRepository scheduleRepository;

    public List<StudentResponse> findAll() {
        return studentRepository
                .findAll()
                .stream()
                .map(student -> modelMapper.map(student, StudentResponse.class))
                .collect(Collectors.toList());
    }

    public StudentResponse findById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Student not found with id -%s", id)
        ));

        StudentResponse studentResponse = modelMapper.map(student, StudentResponse.class);

        studentResponse.setGroups(groupRepository.findAllByStudent(student)
                .stream()
                .map(group -> modelMapper.map(group, GroupResponse.class))
                .collect(Collectors.toList())
        );

        studentResponse.setLecturers(lecturerRepository.findAllByStudent(student)
                .stream()
                .map(lecturer -> modelMapper.map(lecturer, LecturerResponse.class))
                .collect(Collectors.toList()));

        studentResponse.setLessons(lessonRepository.findAllByStudent(student)
                .stream()
                .map(lesson -> modelMapper.map(lesson, LessonResponse.class))
                .collect(Collectors.toList()));

        studentResponse.setSchedules(scheduleRepository.findAllByStudent(student)
                .stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleResponse.class))
                .collect(Collectors.toList()));

        return studentResponse;
    }

    public StudentRequest save(StudentRequest studentRequest) {
        Student student = modelMapper.map(studentRequest, Student.class);

        return modelMapper.map(studentRepository.save(student), StudentRequest.class);
    }

    public StudentRequest update(StudentRequest studentRequest, Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Student not found with id -%s", id)
        ));

        modelMapper.map(studentRequest, student, "map");
        student.setId(id);

        return modelMapper.map(studentRepository.save(student), StudentRequest.class);
    }

    public void delete(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Student not found with id -%s", id)
        ));

        studentRepository.delete(student);
    }
}
