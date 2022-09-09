package com.nicat.schoolmanagementsystem.service;

import com.nicat.schoolmanagementsystem.entity.Lecturer;
import com.nicat.schoolmanagementsystem.entity.Schedule;
import com.nicat.schoolmanagementsystem.entity.Student;
import com.nicat.schoolmanagementsystem.exception.NotFoundException;
import com.nicat.schoolmanagementsystem.repository.LecturerRepository;
import com.nicat.schoolmanagementsystem.repository.ScheduleRepository;
import com.nicat.schoolmanagementsystem.repository.StudentRepository;
import com.nicat.schoolmanagementsystem.request.ScheduleRequest;
import com.nicat.schoolmanagementsystem.response.ScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;
    private final LecturerRepository lecturerRepository;
    private final StudentRepository studentRepository;

    public List<ScheduleResponse> findAll() {
        return scheduleRepository
                .findAll()
                .stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleResponse.class))
                .collect(Collectors.toList());
    }

    public ScheduleResponse findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Schedule not found with id - %s", id)
        ));

        return modelMapper.map(scheduleRepository.save(schedule), ScheduleResponse.class);
    }

    public ScheduleRequest save(ScheduleRequest scheduleRequest) {
        Student student = studentRepository.findById(scheduleRequest.getStudentId()).orElseThrow(() -> new NotFoundException(
                String.format("Student not found with id - %s", scheduleRequest.getStudentId())
        ));

        Lecturer lecturer = lecturerRepository.findById(scheduleRequest.getLecturerId()).orElseThrow(() -> new NotFoundException(
                String.format("Lecturer not found with id - %s", scheduleRequest.getLecturerId())
        ));

        Schedule schedule = modelMapper.map(scheduleRequest, Schedule.class);
        schedule.setStudent(student);
        schedule.setLecturer(lecturer);

        return modelMapper.map(scheduleRepository.save(schedule), ScheduleRequest.class);
    }

    public ScheduleRequest update(ScheduleRequest scheduleRequest, Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Schedule not found with id - %s", id)
        ));

        modelMapper.map(scheduleRequest, schedule, "map");
        schedule.setId(id);

        return modelMapper.map(scheduleRepository.save(schedule), ScheduleRequest.class);
    }

    public void delete(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Schedule not found with id - %s", id)
        ));

        scheduleRepository.delete(schedule);
    }
}
