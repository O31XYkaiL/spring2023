package com.webagregator.webagregator.app;

import com.webagregator.webagregator.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StudentService {
    private final JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    public StudentService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // метод для получения инфы о студенте по его/ее id
    public Student getStudentById(Long id) {
        logger.info("Getting student by ID: " + id);
        String sql = "SELECT * FROM students WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Student student = new Student();
            student.setId(rs.getLong("id"));
            student.setEmail(rs.getString("email"));
            student.setPassword(rs.getString("password"));
            student.setFirstName(rs.getString("first_name"));
            student.setLastName(rs.getString("last_name"));
            student.setAcademicGroup(rs.getString("academic_group"));
            return student;
        });
    }

    // метод для создания нового студента в бд
    public void createStudent(Student student) {
        logger.info("Creating a new student: " + student);
        String sql = "INSERT INTO students (email, password, first_name, last_name, academic_group) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, student.getEmail(), student.getPassword(), student.getFirstName(), student.getLastName(), student.getAcademicGroup());
    }

    // метод для обновления инфы об уже существующем студенте
    public void updateStudent(Student student) {
        logger.info("Updating student: " + student);
        String sql = "UPDATE students SET email = ?, password = ?, first_name = ?, last_name = ?, academic_group = ? WHERE id = ?";
        jdbcTemplate.update(sql, student.getEmail(), student.getPassword(), student.getFirstName(), student.getLastName(), student.getAcademicGroup(), student.getId());
    }

    // метод для удаления существующего студента из бд
    public void deleteStudent(Long id) {
        logger.info("Deleting student by ID: " + id);
        String sql = "DELETE FROM students WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}