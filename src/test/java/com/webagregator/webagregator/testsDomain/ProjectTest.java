package com.webagregator.webagregator.testsDomain;

import com.webagregator.webagregator.domain.Admin;
import com.webagregator.webagregator.domain.Project;
import com.webagregator.webagregator.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    @Test
    public void testProjectId() {
        Project project = new Project();
        project.setId(1L);

        assertEquals(1L, project.getId());
    }

    @Test
    public void testProjectName() {
        Project project = new Project();
        project.setProjectName("Test Project");

        assertEquals("Test Project", project.getProjectName());
    }

    @Test
    public void testProjectDescription() {
        Project project = new Project();
        project.setProjectDescription("Description of the project");

        assertEquals("Description of the project", project.getProjectDescription());
    }

    @Test
    public void testHowToPlay() {
        Project project = new Project();
        project.setHowToPlay("Instructions on how to play the project");

        assertEquals("Instructions on how to play the project", project.getHowToPlay());
    }

    @Test
    public void testCoverImage() {
        Project project = new Project();
        project.setCoverImage("cover.jpg");

        assertEquals("cover.jpg", project.getCoverImage());
    }

    @Test
    public void testGameplayVideo() {
        Project project = new Project();
        project.setGameplayVideo("gameplay.mp4");

        assertEquals("gameplay.mp4", project.getGameplayVideo());
    }

    @Test
    public void testProjectTheme() {
        Project project = new Project();
        project.setProjectTheme("Sci-Fi");

        assertEquals("Sci-Fi", project.getProjectTheme());
    }

    @Test
    public void testRepositoryLink() {
        Project project = new Project();
        project.setRepositoryLink("https://github.com/user/project");

        assertEquals("https://github.com/user/project", project.getRepositoryLink());
    }

    @Test
    public void testProjectArchivePath() {
        String archivePath = "path/to/archive.zip";
        Project project = new Project();
        project.setProjectArchivePath(archivePath);

        assertNotNull(project.getProjectArchivePath());
        assertEquals(archivePath, project.getProjectArchivePath());
    }

    @Test
    public void testCreator() {
        Project project = new Project();
        Student student = new Student();
        student.setId(1L);
        project.setCreator(student);

        assertEquals(1L, project.getCreator().getId());
    }

    @Test
    public void testModerator() {
        Project project = new Project();
        Admin admin = new Admin();
        admin.setId(1L);
        project.setModerator(admin);

        assertEquals(1L, project.getModerator().getId());
    }

    @Test
    public void testVoteCount() {
        Project project = new Project();
        project.setVoteCount(10);

        assertEquals(10, project.getVoteCount());
    }
    @Test
    public void testProjectCategory() {
        Project project = new Project();
        String projectCategory = "Category A";
        project.setProjectCategory(projectCategory);
        assertEquals(projectCategory, project.getProjectCategory());
    }

    @Test
    public void testProjectSubcategory() {
        Project project = new Project();
        String projectSubcategory = "Subcategory B";
        project.setProjectSubcategory(projectSubcategory);
        assertEquals(projectSubcategory, project.getProjectSubcategory());
    }
}
