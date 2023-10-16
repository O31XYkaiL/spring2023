package com.webagregator.webagregator.testsDomain;

import com.webagregator.webagregator.domain.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectTest {
    private Project project;
    @BeforeEach
    void setUp() {
        project = new Project();
    }

    @Test
    void testProjectSetterGetter() {
        project.setProjectName("Test Project");
        project.setProjectDescription("This is a test project.");
        project.setHowToPlay("Instructions for playing.");
        project.setCoverImage("cover.jpg");
        project.setGameplayVideo("video.mp4");
        project.setProjectCategory("Game");
        project.setProjectTheme("Adventure");
        project.setRepositoryLink("https://github.com/test/test-project");

        assertEquals("Test Project", project.getProjectName());
        assertEquals("This is a test project.", project.getProjectDescription());
        assertEquals("Instructions for playing.", project.getHowToPlay());
        assertEquals("cover.jpg", project.getCoverImage());
        assertEquals("video.mp4", project.getGameplayVideo());
        assertEquals("Game", project.getProjectCategory());
        assertEquals("Adventure", project.getProjectTheme());
        assertEquals("https://github.com/test/test-project", project.getRepositoryLink());
    }
}
