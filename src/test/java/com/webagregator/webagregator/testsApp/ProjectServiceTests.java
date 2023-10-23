package com.webagregator.webagregator.testsApp;

import com.webagregator.webagregator.app.repositories.ProjectRepository;
import com.webagregator.webagregator.app.services.ProjectService;
import com.webagregator.webagregator.domain.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProjectServiceTests {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProjectById() {
        Long projectId = 1L;
        Project project = new Project();
        project.setId(projectId);

        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        Project result = projectService.getProjectById(projectId);

        assertNotNull(result);
        assertEquals(projectId, result.getId());
    }

    @Test
    public void testGetProjectById_WhenProjectNotFound() {
        Long projectId = 1L;

        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        Project result = projectService.getProjectById(projectId);

        assertNull(result);
    }

    @Test
    public void testCreateProject() {
        Project project = new Project();

        Project result = projectService.createProject(project);

        assertNotNull(result);
        Mockito.verify(projectRepository).save(project);
    }

    @Test
    public void testUpdateProject() {
        Project project = new Project();

        Project result = projectService.updateProject(project);

        assertNotNull(result);
        Mockito.verify(projectRepository).save(project);
    }

    @Test
    public void testDeleteProject() {
        Long projectId = 1L;

        projectService.deleteProject(projectId);

        Mockito.verify(projectRepository).deleteById(projectId);
    }
}
