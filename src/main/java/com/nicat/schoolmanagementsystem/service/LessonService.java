package com.nicat.schoolmanagementsystem.service;

import com.nicat.schoolmanagementsystem.entity.Lecturer;
import com.nicat.schoolmanagementsystem.entity.Lesson;
import com.nicat.schoolmanagementsystem.entity.Student;
import com.nicat.schoolmanagementsystem.exception.NotFoundException;
import com.nicat.schoolmanagementsystem.repository.LecturerRepository;
import com.nicat.schoolmanagementsystem.repository.LessonRepository;
import com.nicat.schoolmanagementsystem.repository.StudentRepository;
import com.nicat.schoolmanagementsystem.request.LessonRequest;
import com.nicat.schoolmanagementsystem.response.LessonResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;
    private final LecturerRepository lecturerRepository;
    private final StudentRepository studentRepository;

    public List<LessonResponse> findAll() {
        return lessonRepository
                .findAll()
                .stream()
                .map(lesson -> modelMapper.map(lesson, LessonResponse.class))
                .collect(Collectors.toList());
    }

    public LessonResponse findById(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Lesson not found with id - %s", id)
        ));

        return modelMapper.map(lessonRepository.save(lesson), LessonResponse.class);
    }

    public LessonRequest save(LessonRequest lessonRequest) {
        Student student = studentRepository.findById(lessonRequest.getStudentId()).orElseThrow(() -> new NotFoundException(
                String.format("Student not found with id - %s", lessonRequest.getStudentId())
        ));

        Lecturer lecturer = lecturerRepository.findById(lessonRequest.getLecturerId()).orElseThrow(() -> new NotFoundException(
                String.format("Lecturer not found with id - %s", lessonRequest.getLecturerId())
        ));

        Lesson lesson = modelMapper.map(lessonRequest, Lesson.class);
        lesson.setStudent(student);
        lesson.setLecturer(lecturer);

        return modelMapper.map(lessonRepository.save(lesson), LessonRequest.class);
    }

    public LessonRequest update(LessonRequest lessonRequest, Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Lesson not found with id - %s", id)
        ));

        modelMapper.map(lessonRequest, lesson, "map");
        lesson.setId(id);

        return modelMapper.map(lessonRepository.save(lesson), LessonRequest.class);
    }

    public void delete(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Lesson not found with id - %s", id)
        ));

        lessonRepository.delete(lesson);
    }
}
