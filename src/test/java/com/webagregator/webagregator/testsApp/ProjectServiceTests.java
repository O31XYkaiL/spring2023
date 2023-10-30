package com.webagregator.webagregator.testsApp;

import com.webagregator.webagregator.app.repositories.ProjectRepository;
import com.webagregator.webagregator.app.repositories.StudentRepository;
import com.webagregator.webagregator.app.services.ProjectService;
import com.webagregator.webagregator.domain.Project;
import com.webagregator.webagregator.domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectServiceTests {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProjectById() {
        Long projectId = 1L;
        Project project = new Project();
        project.setId(projectId);

        Mockito.when(projectRepository.findById(projectId)).thenReturn(java.util.Optional.of(project));

        Project result = projectService.getProjectById(projectId);

        assertNotNull(result);
        assertEquals(projectId, result.getId());
    }

    @Test
    public void testGetProjectById_WhenProjectNotFound() {
        Long projectId = 1L;

        Mockito.when(projectRepository.findById(projectId)).thenReturn(java.util.Optional.empty());

        Project result = projectService.getProjectById(projectId);

        assertNull(result);
    }

    @Test
    public void testCreateProject() {
        Project project = new Project();

        projectService.createProject(project);

        Mockito.verify(projectRepository).save(project);
    }

    @Test
    public void testUpdateProject() {
        Project project = new Project();

        projectService.updateProject(project);

        Mockito.verify(projectRepository).save(project);
    }

    @Test
    public void testDeleteProject() {
        Long projectId = 1L;

        projectService.deleteProject(projectId);

        Mockito.verify(projectRepository).deleteById(projectId);
    }

    @Test
    public void testIncreaseVoteCount() {
        Long projectId = 1L;
        int voteCount = 5;

        Project project = new Project();
        project.setId(projectId);
        project.setVoteCount(10);

        Mockito.when(projectRepository.findById(projectId)).thenReturn(java.util.Optional.of(project));

        projectService.increaseVoteCount(projectId, voteCount);

        assertEquals(15, project.getVoteCount());
        Mockito.verify(projectRepository).save(project);
    }

    @Test
    public void testEditProjectByTeamLeader_WhenStudentIsNotTeamLeader() {
        Long studentId = 1L;
        Long projectId = 2L;

        Student student = new Student();
        student.setId(studentId);
        student.setRoleInProject("Student");

        Project project = new Project();
        project.setId(projectId);
        project.setCreator(student);

        Mockito.when(studentRepository.findById(studentId)).thenReturn(java.util.Optional.of(student));
        Mockito.when(projectRepository.findById(projectId)).thenReturn(java.util.Optional.of(project));

        Project updatedProject = new Project();
        updatedProject.setProjectName("Updated Project");
        updatedProject.setProjectDescription("Updated Description");

        Project result = projectService.editProjectByTeamLeader(studentId, projectId, updatedProject);

        assertNull(result);
    }

    @Test
    public void testEditProjectByTeamLeader_WhenStudentIsTeamLeader() {
        Long studentId = 1L;
        Long projectId = 2L;

        Student student = new Student();
        student.setId(studentId);
        student.setRoleInProject("Team Leader");

        Project project = new Project();
        project.setId(projectId);
        project.setCreator(student);

        Mockito.when(studentRepository.findById(studentId)).thenReturn(java.util.Optional.of(student));
        Mockito.when(projectRepository.findById(projectId)).thenReturn(java.util.Optional.of(project));

        Project updatedProject = new Project();
        updatedProject.setProjectName("Updated Project");
        updatedProject.setProjectDescription("Updated Description");

        Project result = projectService.editProjectByTeamLeader(studentId, projectId, updatedProject);

        assertNotNull(result);
        assertEquals("Updated Project", result.getProjectName());
        assertEquals("Updated Description", result.getProjectDescription());
        Mockito.verify(projectRepository).save(project);
    }
}