package com.webagregator.webagregator.domain;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {

    @Test
    public void testAdminId() {
        Admin admin = new Admin();
        admin.setId(1L);

        assertEquals(1L, admin.getId());
    }

    @Test
    public void testAdminUsername() {
        Admin admin = new Admin();
        admin.setUsername("adminUser");

        assertEquals("adminUser", admin.getUsername());
    }

    @Test
    public void testAdminPassword() {
        Admin admin = new Admin();
        admin.setPassword("adminPass");

        assertEquals("adminPass", admin.getPassword());
    }

    @Test
    public void testProjectsForReview() {
        Admin admin = new Admin();
        Project project1 = new Project();
        Project project2 = new Project();

        admin.setProjectsForReview(List.of(project1, project2));

        List<Project> projects = admin.getProjectsForReview();

        assertNotNull(projects);
        assertEquals(2, projects.size());
        assertTrue(projects.contains(project1));
        assertTrue(projects.contains(project2));
    }
}
