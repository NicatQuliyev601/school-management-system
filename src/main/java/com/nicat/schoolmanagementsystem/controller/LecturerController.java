package com.nicat.schoolmanagementsystem.controller;

import com.nicat.schoolmanagementsystem.request.GroupRequest;
import com.nicat.schoolmanagementsystem.request.LecturerRequest;
import com.nicat.schoolmanagementsystem.response.GroupResponse;
import com.nicat.schoolmanagementsystem.response.LecturerResponse;
import com.nicat.schoolmanagementsystem.service.GroupService;
import com.nicat.schoolmanagementsystem.service.LecturerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lecturers")
public class LecturerController {

    private final LecturerService lecturerService;

    @GetMapping
    @PreAuthorize("hasRole('LECTURER')")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<List<LecturerResponse>> findAll() {
        return ResponseEntity.ok(lecturerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LecturerResponse> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(lecturerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<LecturerRequest> create(@RequestBody LecturerRequest lecturerRequest) {
        return ResponseEntity.ok(lecturerService.save(lecturerRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LecturerRequest> update(@PathVariable("id") Long id, @RequestBody LecturerRequest lecturerRequest) {
        return ResponseEntity.ok(lecturerService.update(lecturerRequest, id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        lecturerService.delete(id);
    }
}
