package com.webagregator.webagregator.testsApp;

import com.webagregator.webagregator.domain.ProjectRole;
import com.webagregator.webagregator.app.repositories.ProjectRepository;
import com.webagregator.webagregator.app.repositories.StudentRepository;
import com.webagregator.webagregator.app.services.ProjectService;
import com.webagregator.webagregator.domain.Project;
import com.webagregator.webagregator.domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

        when(projectRepository.findById(projectId)).thenReturn(java.util.Optional.of(project));

        Project result = projectService.getProjectById(projectId);

        assertNotNull(result);
        assertEquals(projectId, result.getId());
    }

    @Test
    public void testGetProjectById_WhenProjectNotFound() {
        Long projectId = 1L;

        when(projectRepository.findById(projectId)).thenReturn(java.util.Optional.empty());

        Project result = projectService.getProjectById(projectId);

        assertNull(result);
    }

    @Test
    public void testCreateProject() {
        Project project = new Project();

        projectService.createProject(project);

        verify(projectRepository).save(project);
    }

    @Test
    public void testUpdateProject() {
        Project project = new Project();

        projectService.updateProject(project);

        verify(projectRepository).save(project);
    }

    @Test
    public void testDeleteProject() {
        Long projectId = 1L;

        projectService.deleteProject(projectId);

        verify(projectRepository).deleteById(projectId);
    }

    @Test
    public void testEditProjectByTeamLeader_WhenStudentIsNotTeamLeader() {
        Long studentId = 1L;
        Long projectId = 2L;

        Student student = new Student();
        student.setId(studentId);
        student.setRoleInProject(ProjectRole.DESIGNER);

        Project project = new Project();
        project.setId(projectId);
        project.setCreator(student);

        when(studentRepository.findById(studentId)).thenReturn(java.util.Optional.of(student));
        when(projectRepository.findById(projectId)).thenReturn(java.util.Optional.of(project));

        Project updatedProject = new Project();
        updatedProject.setProjectName("Updated Project");
        updatedProject.setProjectDescription("Updated Description");

        Project result = projectService.editProjectByTeamLeader(studentId, projectId, updatedProject);

        assertNotNull(result);
        assertEquals("Updated Project", result.getProjectName());
        assertEquals("Updated Description", result.getProjectDescription());
    }

    @Test
    public void testEditProjectByTeamLeader_WhenStudentIsTeamLeader() {
        Long studentId = 1L;
        Long projectId = 2L;

        Student student = new Student();
        student.setId(studentId);
        student.setRoleInProject(ProjectRole.DESIGNER);

        Project project = new Project();
        project.setId(projectId);
        project.setCreator(student);

        when(studentRepository.findById(studentId)).thenReturn(java.util.Optional.of(student));
        when(projectRepository.findById(projectId)).thenReturn(java.util.Optional.of(project));

        Project updatedProject = new Project();
        updatedProject.setProjectName("Updated Project");
        updatedProject.setProjectDescription("Updated Description");

        Project result = projectService.editProjectByTeamLeader(studentId, projectId, updatedProject);

        assertNotNull(result);
        assertEquals("Updated Project", result.getProjectName());
        assertEquals("Updated Description", result.getProjectDescription());
        verify(projectRepository).save(project);
    }

    @Test
    public void testFilterProjectsByCategoryAndSubcategory() {
        String category = "CategoryName";
        String subcategory = "SubcategoryName";

        Project project1 = new Project();
        project1.setProjectCategory(category);
        project1.setProjectSubcategory(subcategory);

        Project project2 = new Project();
        project2.setProjectCategory(category);
        project2.setProjectSubcategory("OtherSubcategory");

        List<Project> projects = List.of(project1, project2);

        when(projectRepository.findByCategoryAndSubcategory(category, subcategory)).thenReturn(Collections.singletonList(project1));

        List<Project> result = projectService.filterProjectsByCategoryAndSubcategory(category, subcategory);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(project1, result.get(0));
    }

    @Test
    public void testUploadProjectArchive() throws IOException {
        Long projectId = 1L;
        Project project = new Project();
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        String tempDirPath = "C:\\Users\\koziy\\IdeaProjects\\spring2023\\src\\projects";
        String tempExtractedDirPath = tempDirPath + "\\" + UUID.randomUUID();
        Files.createDirectories(Path.of(tempExtractedDirPath));

        Path tempArchivePath = Files.createTempFile("test", ".zip");
        createTestZipFile(tempExtractedDirPath, tempArchivePath);

        MultipartFile mockMultipartFile = createMockMultipartFile(tempArchivePath);

        Project result = projectService.uploadProjectArchive(projectId, mockMultipartFile);

        verify(projectRepository, times(1)).save(project);

        assertNotNull(result);
        assertNotNull(result.getProjectArchivePath());

        assertTrue(result.getProjectArchivePath().startsWith(tempDirPath));}

    private void createTestZipFile(String sourceDir, Path destination) throws IOException {
        try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(destination))) {
            Path sourcePath = Path.of(sourceDir);
            Files.walk(sourcePath)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(sourcePath.relativize(path).toString());
                        try {
                            zipOut.putNextEntry(zipEntry);
                            Files.copy(path, zipOut);
                            zipOut.closeEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    private MultipartFile createMockMultipartFile(Path file) throws IOException {
        byte[] fileContent = Files.readAllBytes(file);
        return new MockMultipartFile("file", file.getFileName().toString(), "application/zip", fileContent);
    }

    @Test
    public void testVoteForProject_SuccessfulVote() {

        Student student = new Student();
        student.setId(1L);
        student.setVoteCount(15);

        Project project = new Project();
        project.setId(1L);
        project.setVoteCount(0);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        boolean result = projectService.voteForProject(1L, 1L);

        assertTrue(result);

        assertEquals(10, student.getVoteCount());
        assertEquals(5, project.getVoteCount());
        assertEquals(1, student.getVotedProjects().size());
        assertEquals(1, project.getVoters().size());
    }

    @Test
    void uploadProjectCoverImage_Success() throws IOException {
        Long projectId = 1L;
        byte[] imageData = "Test image data".getBytes();
        MockMultipartFile coverImage = new MockMultipartFile("coverImage", "test.jpg", "image/jpeg", imageData);

        Project project = new Project();
        project.setId(projectId);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        Path tempDir = Files.createTempDirectory("test-images");

        Project resultProject = projectService.uploadProjectCoverImage(projectId, coverImage);

        assertNotNull(resultProject);
        assertEquals(projectId, resultProject.getId());
        assertTrue(Files.exists(Path.of(resultProject.getCoverImage())));
    }

    @Test
    void uploadProjectCoverImage_NullProject() {
        Long projectId = 1L;
        byte[] imageData = "Test image data".getBytes();
        MockMultipartFile coverImage = new MockMultipartFile("coverImage", "test.jpg", "image/jpeg", imageData);

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        Project resultProject = projectService.uploadProjectCoverImage(projectId, coverImage);

        assertNull(resultProject);
    }

    @Test
    void uploadGameplayVideo_Success() throws IOException {
        Long projectId = 1L;
        byte[] videoData = "Test video data".getBytes();
        MockMultipartFile gameplayVideo = new MockMultipartFile("gameplayVideo", "test.mp4", "video/mp4", videoData);

        Project project = new Project();
        project.setId(projectId);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        Path tempDir = Files.createTempDirectory("test-videos");

        Project resultProject = projectService.uploadGameplayVideo(projectId, gameplayVideo);

        assertNotNull(resultProject);
        assertEquals(projectId, resultProject.getId());
        assertTrue(Files.exists(Path.of(resultProject.getGameplayVideo())));

        Files.deleteIfExists(tempDir);
    }

    @Test
    void uploadGameplayVideo_NullProject() {
        Long projectId = 1L;
        byte[] videoData = "Test video data".getBytes();
        MockMultipartFile gameplayVideo = new MockMultipartFile("gameplayVideo", "test.mp4", "video/mp4", videoData);

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        Project resultProject = projectService.uploadGameplayVideo(projectId, gameplayVideo);

        assertNull(resultProject);
    }
}