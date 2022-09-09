package com.nicat.schoolmanagementsystem.service;

import com.nicat.schoolmanagementsystem.entity.Lecturer;
import com.nicat.schoolmanagementsystem.entity.Student;
import com.nicat.schoolmanagementsystem.exception.NotFoundException;
import com.nicat.schoolmanagementsystem.repository.*;
import com.nicat.schoolmanagementsystem.request.LecturerRequest;
import com.nicat.schoolmanagementsystem.response.GroupResponse;
import com.nicat.schoolmanagementsystem.response.LecturerResponse;
import com.nicat.schoolmanagementsystem.response.LessonResponse;
import com.nicat.schoolmanagementsystem.response.ScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LecturerService {
    private final ModelMapper modelMapper;
    private final LecturerRepository lecturerRepository;
    private final StudentRepository studentRepository;
    private final ScheduleRepository scheduleRepository;
    private final LessonRepository lessonRepository;
    private final GroupRepository groupRepository;

    public List<LecturerResponse> findAll() {
        return lecturerRepository
                .findAll()
                .stream()
                .map(lecturer -> modelMapper.map(lecturer, LecturerResponse.class))
                .collect(Collectors.toList());
    }

    public LecturerResponse findById(Long id) {
        Lecturer lecturer = lecturerRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Lecturer not found with id -%s", id)
        ));

        LecturerResponse lecturerResponse = modelMapper.map(lecturer, LecturerResponse.class);

        lecturerResponse.setGroups(groupRepository.findAllByLecturer(lecturer)
                .stream()
                .map(group -> modelMapper.map(group, GroupResponse.class))
                .collect(Collectors.toList())
        );

        lecturerResponse.setLessons(lessonRepository.findAllByLecturer(lecturer)
                .stream()
                .map(lesson -> modelMapper.map(lesson, LessonResponse.class))
                .collect(Collectors.toList())
        );

        lecturerResponse.setSchedules(scheduleRepository.findAllByLecturer(lecturer)
                .stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleResponse.class))
                .collect(Collectors.toList())
        );
        return lecturerResponse;
    }

    public LecturerRequest save(LecturerRequest lecturerRequest) {
        Student student = studentRepository.findById(lecturerRequest.getStudentId()).orElseThrow(() -> new NotFoundException(
                String.format("Student not fount with id -%s", lecturerRequest.getStudentId())
        ));

        Lecturer lecturer = modelMapper.map(lecturerRequest, Lecturer.class);
        lecturer.setStudent(student);

        return modelMapper.map(lecturerRepository.save(lecturer), LecturerRequest.class);
    }

    public LecturerRequest update(LecturerRequest lecturerRequest, Long id) {
        Lecturer lecturer = lecturerRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Lecturer not found with id -%s", id)
        ));
        modelMapper.map(lecturerRequest, lecturer, "map");
        lecturer.setId(id);

        return modelMapper.map(lecturerRepository.save(lecturer), LecturerRequest.class);
    }

    public void delete(Long id) {
        Lecturer lecturer = lecturerRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Lecturer not found with id -%s", id)
        ));
        lecturerRepository.delete(lecturer);
    }
}
