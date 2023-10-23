package com.webagregator.webagregator.app.services;

import com.webagregator.webagregator.app.repositories.StudentRepository;
import com.webagregator.webagregator.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getStudentById(Long id) {
        log.info("Getting student by ID: {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    public void createStudent(Student student) {
        log.info("Creating a new student: {}", student);
        studentRepository.save(student);
    }

    public void updateStudent(Student student) {
        log.info("Updating student: {}", student);
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        log.info("Deleting student by ID: {}", id);
        studentRepository.deleteById(id);
    }
}
