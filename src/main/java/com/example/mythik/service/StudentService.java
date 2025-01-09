package com.example.mythik.service;

import com.example.mythik.model.Student;
import com.example.mythik.model.Subject;
import com.example.mythik.repository.StudentRepository;
import com.example.mythik.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Optional<Student> getStudent(Long studentId) {
        return studentRepository.findById(studentId);
    }

    public Student updateStudentName(Long id, String newName) {

        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isEmpty()) {
            return null;
        }

        Student student = studentOptional.get();
        student.setName(newName);

        return studentRepository.save(student);
    }

    public Optional<Student> assignSubjectToStudent(Long studentId, Long subjectId) {

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
}
