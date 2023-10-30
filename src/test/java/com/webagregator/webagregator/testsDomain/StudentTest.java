package com.webagregator.webagregator.testsDomain;

import com.webagregator.webagregator.domain.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    @Test
    public void testStudentId() {
        Student student = new Student();
        student.setId(1L);

        assertEquals(1L, student.getId());
    }

    @Test
    public void testEmail() {
        Student student = new Student();
        student.setEmail("test@example.com");

        assertEquals("test@example.com", student.getEmail());
    }

    @Test
    public void testPassword() {
        Student student = new Student();
        student.setPassword("password123");

        assertEquals("password123", student.getPassword());
    }

    @Test
    public void testFirstName() {
        Student student = new Student();
        student.setFirstName("John");

        assertEquals("John", student.getFirstName());
    }

    @Test
    public void testLastName() {
        Student student = new Student();
        student.setLastName("Doe");

        assertEquals("Doe", student.getLastName());
    }

    @Test
    public void testAcademicGroup() {
        Student student = new Student();
        student.setAcademicGroup("CS101");

        assertEquals("CS101", student.getAcademicGroup());
    }

    @Test
    public void testVoteCount() {
        Student student = new Student();
        student.setVoteCount(5);

        assertEquals(5, student.getVoteCount());
    }

    @Test
    public void testRoleInProject() {
        Student student = new Student();
        student.setRoleInProject("Team Member");

        assertEquals("Team Member", student.getRoleInProject());
    }
}
