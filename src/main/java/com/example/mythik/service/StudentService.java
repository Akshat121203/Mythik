package com.example.mythik.service;

import com.example.mythik.model.Student;
import com.example.mythik.model.Subject;
import com.example.mythik.repository.StudentRepository;
import com.example.mythik.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public StudentService(StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> getStudent(UUID studentId) {
        return studentRepository.findById(studentId);
    }

    public List<Student> getStudentByName(String name) {
        return studentRepository.findAllByName(name);
    }

    public Student updateStudentName(UUID id, String newName) {

        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isEmpty()) {
            return null;
        }

        Student student = studentOptional.get();
        student.setName(newName);

        return studentRepository.save(student);
    }

    public Optional<Student> assignSubjectToStudent(UUID studentId, UUID subjectId) {

        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isEmpty()) {
            throw new RuntimeException("Student not found");
        }

        Optional<Subject> subjectOptional = subjectRepository.findById(subjectId);
        if (subjectOptional.isEmpty()) {
            throw new RuntimeException("Subject not found");
        }

        Student student = studentOptional.get();
        Subject subject = subjectOptional.get();

        student.addSubject(subject);
        studentRepository.save(student);

        return Optional.of(student);
    }

    public Optional<Student> assignListOfSubjectsToStudent(UUID studentId, List<UUID> subjectIds) {

        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isEmpty()) {
            throw new RuntimeException("Student not found");
        }

        for(UUID subjectId : subjectIds) {
            Optional<Subject> subjectOptional = subjectRepository.findById(subjectId);
            if (subjectOptional.isEmpty()) {
                throw new RuntimeException("Subject not found");
            }

            Student student = studentOptional.get();
            Subject subject = subjectOptional.get();

            student.addSubject(subject);
            studentRepository.save(student);
        }

        return studentOptional;

    }
}
