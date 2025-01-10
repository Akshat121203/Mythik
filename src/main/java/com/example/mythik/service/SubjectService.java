package com.example.mythik.service;

import com.example.mythik.model.Subject;
import com.example.mythik.repository.StudentRepository;
import com.example.mythik.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    public SubjectService(SubjectRepository subjectRepository, StudentRepository studentRepository) {
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
    }

    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }

    public void deleteSubject(UUID subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);

        if (subject.isEmpty()) {
            throw new RuntimeException("Subject not found");
        }

        boolean isAssigned = studentRepository.findAll().stream()
                .anyMatch(student -> student.getSubjects().contains(subject.get()));

        if (isAssigned) {
            throw new RuntimeException("Cannot delete subject. It is assigned to one or more students.");
        }

        subjectRepository.deleteById(subjectId);
    }
}
