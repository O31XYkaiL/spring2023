package com.webagregator.webagregator.app.repositories;

import com.webagregator.webagregator.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findStudentByLastNameAndFirstName(String lastName, String firstName);
}
