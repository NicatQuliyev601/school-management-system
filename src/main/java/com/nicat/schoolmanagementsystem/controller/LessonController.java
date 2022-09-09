package com.nicat.schoolmanagementsystem.controller;

import com.nicat.schoolmanagementsystem.request.GroupRequest;
import com.nicat.schoolmanagementsystem.request.LessonRequest;
import com.nicat.schoolmanagementsystem.response.GroupResponse;
import com.nicat.schoolmanagementsystem.response.LessonResponse;
import com.nicat.schoolmanagementsystem.service.GroupService;
import com.nicat.schoolmanagementsystem.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lessons")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping
    public ResponseEntity<List<LessonResponse>> findAll() {
        return ResponseEntity.ok(lessonService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonResponse> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(lessonService.findById(id));
    }

    @PostMapping
    public ResponseEntity<LessonRequest> create(@RequestBody LessonRequest lessonRequest) {
        return ResponseEntity.ok(lessonService.save(lessonRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonRequest> update(@PathVariable("id") Long id, @RequestBody LessonRequest lessonRequest) {
        return ResponseEntity.ok(lessonService.update(lessonRequest, id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        lessonService.delete(id);
    }
}
