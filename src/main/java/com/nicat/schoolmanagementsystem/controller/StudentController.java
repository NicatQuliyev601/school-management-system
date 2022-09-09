package com.nicat.schoolmanagementsystem.controller;

import com.nicat.schoolmanagementsystem.request.ScheduleRequest;
import com.nicat.schoolmanagementsystem.request.StudentRequest;
import com.nicat.schoolmanagementsystem.response.ScheduleResponse;
import com.nicat.schoolmanagementsystem.response.StudentResponse;
import com.nicat.schoolmanagementsystem.service.ScheduleService;
import com.nicat.schoolmanagementsystem.service.StudentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    @PreAuthorize("hasRole('STUDENT')")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<List<StudentResponse>> findAll() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<StudentRequest> create(@RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(studentService.save(studentRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentRequest> update(@PathVariable("id") Long id, @RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(studentService.update(studentRequest, id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        studentService.delete(id);
    }
}
