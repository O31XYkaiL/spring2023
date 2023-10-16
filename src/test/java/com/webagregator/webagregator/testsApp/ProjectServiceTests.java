package com.webagregator.webagregator.testsApp;

import com.webagregator.webagregator.app.ProjectService;
import com.webagregator.webagregator.domain.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProjectServiceTests {

    @MockBean
    private JdbcTemplate jdbcTemplate;

    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService(jdbcTemplate);
    }

    @Test
    public void testGetProjectById() {
        // Arrange
        Long projectId = 1L;
        Project expectedProject = new Project();
        expectedProject.setId(projectId);
        expectedProject.setProjectName("Test Project");

        // Mocking jdbcTemplate behavior
        when(jdbcTemplate.queryForObject(anyString(), eq(Project.class), any(Object[].class))).thenReturn(expectedProject);

        // Act
        Project actualProject = projectService.getProjectById(projectId);

        // Assert
        assertEquals(expectedProject, actualProject);
    }

    @Test
    public void testCreateProject() {
        // Arrange
        Project projectToCreate = new Project();
        projectToCreate.setProjectName("New Project");

        // Act
        projectService.createProject(projectToCreate);

        // Assert
        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }

    @Test
    public void testUpdateProject() {
        // Arrange
        Project projectToUpdate = new Project();
        projectToUpdate.setId(1L);
        projectToUpdate.setProjectName("Updated Project");

        // Act
        projectService.updateProject(projectToUpdate);

        // Assert
        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }

    @Test
    public void testDeleteProject() {
        // Arrange
        Long projectId = 1L;

        // Act
        projectService.deleteProject(projectId);

        // Assert
        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }
}
