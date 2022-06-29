package com.appvam.api.controller;

import com.appvam.api.models.Assignment;
import com.appvam.api.service.AssignmentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v2/assignments")
public class AssignmentController {

    private final AssignmentsService assignmentsService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) String receiver) {
        return ResponseEntity
                .ok(assignmentsService.getAll(receiver));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity
                .ok(assignmentsService.get(id));
    }

    @PostMapping
    public ResponseEntity<?> assign(@RequestBody Assignment assignment) {
        assignmentsService.send(assignment);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        assignmentsService.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }

}
