package com.webagregator.webagregator.testsApp;

import com.webagregator.webagregator.app.repositories.ProjectRepository;
import com.webagregator.webagregator.app.repositories.StudentRepository;
import com.webagregator.webagregator.app.services.ProjectService;
import com.webagregator.webagregator.app.services.StudentService;
import com.webagregator.webagregator.domain.Project;
import com.webagregator.webagregator.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTests {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ProjectService projectService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetStudentById() {
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);

        Mockito.when(studentRepository.findById(studentId)).thenReturn(java.util.Optional.of(student));

        Student result = studentService.getStudentById(studentId);

        assertNotNull(result);
        assertEquals(studentId, result.getId());
    }

    @Test
    public void testGetStudentById_WhenStudentNotFound() {
        Long studentId = 1L;

        Mockito.when(studentRepository.findById(studentId)).thenReturn(java.util.Optional.empty());

        Student result = studentService.getStudentById(studentId);

        assertNull(result);
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student();

        studentService.createStudent(student);

        Mockito.verify(studentRepository).save(student);
    }

    @Test
    public void testUpdateStudent() {
        Student student = new Student();

        studentService.updateStudent(student);

        Mockito.verify(studentRepository).save(student);
    }

    @Test
    public void testDeleteStudent() {
        Long studentId = 1L;

        studentService.deleteStudent(studentId);

        Mockito.verify(studentRepository).deleteById(studentId);
    }

    @Test
    public void testGetAvailableVoteCount() {
        Long studentId = 1L;
        Student student = new Student();
        student.setVoteCount(5);

        Mockito.when(studentRepository.findById(studentId)).thenReturn(java.util.Optional.of(student));

        int result = studentService.getAvailableVoteCount(studentId);

        assertEquals(5, result);
    }

    @Test
    public void testGetAvailableVoteCount_WhenStudentNotFound() {
        Long studentId = 1L;

        Mockito.when(studentRepository.findById(studentId)).thenReturn(java.util.Optional.empty());

        int result = studentService.getAvailableVoteCount(studentId);

        assertEquals(0, result);
    }
}
