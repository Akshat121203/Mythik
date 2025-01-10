package com.example.mythik.controller;

import com.example.mythik.model.Subject;
import com.example.mythik.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        if (subject == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(subjectService.createSubject(subject));
    }

    @GetMapping
    public ResponseEntity<List<Subject>> getSubjects() {
        return ResponseEntity.ok(subjectService.getSubjects());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable UUID id) {
        try {
            subjectService.deleteSubject(id);
            return ResponseEntity.ok("Subject deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
