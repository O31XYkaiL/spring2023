package com.webagregator.webagregator.testsApp;

import com.webagregator.webagregator.app.StudentService;
import com.webagregator.webagregator.domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StudentServiceTests {

    @MockBean
    private JdbcTemplate jdbcTemplate;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(jdbcTemplate);
    }

    @Test
    public void testGetStudentById() {
        // Arrange
        Long studentId = 1L;
        Student expectedStudent = new Student();
        expectedStudent.setId(studentId);
        expectedStudent.setEmail("test@example.com");
        expectedStudent.setFirstName("John");
        expectedStudent.setLastName("Doe");
        expectedStudent.setAcademicGroup("CS101");

        // Mocking jdbcTemplate behavior
        when(jdbcTemplate.queryForObject(anyString(), eq(Student.class), any(Object[].class))).thenReturn(expectedStudent);

        // Act
        Student actualStudent = studentService.getStudentById(studentId);

        // Assert
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    public void testCreateStudent() {
        // Arrange
        Student studentToCreate = new Student();
        studentToCreate.setEmail("test@example.com");
        studentToCreate.setFirstName("John");
        studentToCreate.setLastName("Doe");
        studentToCreate.setAcademicGroup("CS101");

        // Act
        studentService.createStudent(studentToCreate);

        // Assert
        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }

    @Test
    public void testUpdateStudent() {
        // Arrange
        Student studentToUpdate = new Student();
        studentToUpdate.setId(1L);
        studentToUpdate.setEmail("test@example.com");
        studentToUpdate.setFirstName("John");
        studentToUpdate.setLastName("Doe");
        studentToUpdate.setAcademicGroup("CS101");

        // Act
        studentService.updateStudent(studentToUpdate);

        // Assert
        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }

    @Test
    public void testDeleteStudent() {
        // Arrange
        Long studentId = 1L;

        // Act
        studentService.deleteStudent(studentId);

        // Assert
        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }
}
