package com.webagregator.webagregator.testsDomain;
import com.webagregator.webagregator.domain.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StudentTest {
    @Test
    public void testGettersAndSetters() {
        Student student = new Student();
        student.setId(1L);
        student.setEmail("student1@example.com");
        student.setPassword("password1");
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setAcademicGroup("Group A");

        assertEquals(1L, student.getId());
        assertEquals("student1@example.com", student.getEmail());
        assertEquals("password1", student.getPassword());
        assertEquals("John", student.getFirstName());
        assertEquals("Doe", student.getLastName());
        assertEquals("Group A", student.getAcademicGroup());
    }

    @Test
    public void testEquals() {
        Student student1 = new Student();
        student1.setId(1L);

        Student student2 = new Student();
        student2.setId(1L);

        assertEquals(student1, student2);

        Student student3 = new Student();
        student3.setId(2L);

        assertNotEquals(student1, student3);
    }

    @Test
    public void testHashCode() {
        Student student1 = new Student();
        student1.setId(1L);

        Student student2 = new Student();
        student2.setId(1L);

        assertEquals(student1.hashCode(), student2.hashCode());

        Student student3 = new Student();
        student3.setId(2L);

        assertNotEquals(student1.hashCode(), student3.hashCode());
    }

    @Test
    public void testToString() {
        Student student = new Student();
        student.setId(1L);
        student.setEmail("student1@example.com");
        student.setPassword("password1");
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setAcademicGroup("Group A");

        String expected = "Student(id=1, email=student1@example.com, password=password1, firstName=John, lastName=Doe, academicGroup=Group A)";
        assertEquals(expected, student.toString());
    }
}

