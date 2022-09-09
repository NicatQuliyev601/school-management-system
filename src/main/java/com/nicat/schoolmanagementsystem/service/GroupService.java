package com.nicat.schoolmanagementsystem.service;

import com.nicat.schoolmanagementsystem.entity.Group;
import com.nicat.schoolmanagementsystem.entity.Lecturer;
import com.nicat.schoolmanagementsystem.entity.Student;
import com.nicat.schoolmanagementsystem.exception.NotFoundException;
import com.nicat.schoolmanagementsystem.repository.GroupRepository;
import com.nicat.schoolmanagementsystem.repository.LecturerRepository;
import com.nicat.schoolmanagementsystem.repository.StudentRepository;
import com.nicat.schoolmanagementsystem.request.GroupRequest;
import com.nicat.schoolmanagementsystem.response.GroupResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;
    private final LecturerRepository lecturerRepository;
    private final StudentRepository studentRepository;

    public List<GroupResponse> findAll() {
        return groupRepository
                .findAll()
                .stream()
                .map(group -> modelMapper.map(group, GroupResponse.class))
                .collect(Collectors.toList());
    }

    public GroupResponse findById(Long id) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Group not found with id - %s", id)
        ));

        return modelMapper.map(groupRepository.save(group), GroupResponse.class);
    }

    public GroupRequest save(GroupRequest groupRequest) {
        Student student = studentRepository.findById(groupRequest.getStudentId()).orElseThrow(() -> new NotFoundException(
                String.format("Student not found with id - %s", groupRequest.getStudentId())
        ));

        Lecturer lecturer = lecturerRepository.findById(groupRequest.getLecturerId()).orElseThrow(() -> new NotFoundException(
                String.format("Lecturer not found with id - %s", groupRequest.getLecturerId())
        ));

        Group group = modelMapper.map(groupRequest, Group.class);
        group.setStudent(student);
        group.setLecturer(lecturer);

        return modelMapper.map(groupRepository.save(group), GroupRequest.class);
    }

    public GroupRequest update(GroupRequest groupRequest, Long id) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Group not found with id - %s", id)
        ));

        modelMapper.map(groupRequest, group, "map");
        group.setId(id);

        return modelMapper.map(groupRepository.save(group), GroupRequest.class);
    }

    public void delete(Long id) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Group not found with id - %s", id)
        ));

        groupRepository.delete(group);
    }
}
