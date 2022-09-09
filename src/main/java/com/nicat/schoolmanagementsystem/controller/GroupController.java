package com.nicat.schoolmanagementsystem.controller;

import com.nicat.schoolmanagementsystem.request.GroupRequest;
import com.nicat.schoolmanagementsystem.response.GroupResponse;
import com.nicat.schoolmanagementsystem.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<List<GroupResponse>> findAll() {
        return ResponseEntity.ok(groupService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(groupService.findById(id));
    }

    @PostMapping
    public ResponseEntity<GroupRequest> create(@RequestBody GroupRequest groupRequest) {
        return ResponseEntity.ok(groupService.save(groupRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupRequest> update(@PathVariable("id") Long id, @RequestBody GroupRequest groupRequest) {
        return ResponseEntity.ok(groupService.update(groupRequest, id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        groupService.delete(id);
    }
}
