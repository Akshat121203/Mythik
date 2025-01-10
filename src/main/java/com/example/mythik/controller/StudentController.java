package com.example.mythik.controller;


import com.example.mythik.model.Student;
import com.example.mythik.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        if (student == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable UUID id) {
        return studentService.getStudent(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public List<Student> getStudentByName(@PathVariable String name) {
        return studentService.getStudentByName(name);
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudentName(@PathVariable UUID id, @RequestBody Map<String, String> request) {

        String newName = request.get("name");
        Student student = studentService.updateStudentName(id, newName);

        if(student == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(student);
    }

    @PostMapping("/{studentId}/subjects/{subjectId}")
    public ResponseEntity<Student> assignSubjectToStudent(@PathVariable UUID studentId, @PathVariable UUID subjectId) {
        try {
            return studentService.assignSubjectToStudent(studentId, subjectId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/{studentId}/subjects")
    public ResponseEntity<Student> assignListOfSubjectToStudent(@PathVariable UUID studentId, @RequestBody Map<String,List<UUID>> request) {
        List<UUID> subjectIds = request.get("subject");
        studentService.assignListOfSubjectsToStudent(studentId, subjectIds);
        try {
            return studentService.assignListOfSubjectsToStudent(studentId, subjectIds)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
