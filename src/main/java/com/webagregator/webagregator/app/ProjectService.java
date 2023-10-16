package com.webagregator.webagregator.app;

import com.webagregator.webagregator.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProjectService {
    private final JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    public ProjectService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // метод для получения инфы о проекте по его id
    public Project getProjectById(Long id) {
        logger.info("Getting project by ID: " + id);
        String sql = "SELECT * FROM projects WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Project project = new Project();
            project.setId(rs.getLong("id"));
            project.setProjectName(rs.getString("project_name"));
            project.setProjectDescription(rs.getString("project_description"));
            project.setHowToPlay(rs.getString("how_to_play"));
            project.setCoverImage(rs.getString("cover_image"));
            project.setGameplayVideo(rs.getString("gameplay_video"));
            project.setProjectCategory(rs.getString("project_category"));
            project.setProjectTheme(rs.getString("project_theme"));
            project.setRepositoryLink(rs.getString("repository_link"));
            project.setProjectArchive(rs.getBytes("project_archive"));
            return project;
        });
    }

    // метод для создания нового проекта в бд
    public void createProject(Project project) {
        logger.info("Creating a new project: " + project);
        String sql = "INSERT INTO projects (project_name, project_description, how_to_play, cover_image, gameplay_video, project_category, project_theme, repository_link, project_archive) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, project.getProjectName(), project.getProjectDescription(), project.getHowToPlay(), project.getCoverImage(), project.getGameplayVideo(), project.getProjectCategory(), project.getProjectTheme(), project.getRepositoryLink(), project.getProjectArchive());
    }

    // метод для обновления инфы об уже существующем проекте
    public void updateProject(Project project) {
        logger.info("Updating project: " + project);
        String sql = "UPDATE projects SET project_name = ?, project_description = ?, how_to_play = ?, cover_image = ?, gameplay_video = ?, project_category = ?, project_theme = ?, repository_link = ?, project_archive = ? WHERE id = ?";
        jdbcTemplate.update(sql, project.getProjectName(), project.getProjectDescription(), project.getHowToPlay(), project.getCoverImage(), project.getGameplayVideo(), project.getProjectCategory(), project.getProjectTheme(), project.getRepositoryLink(), project.getProjectArchive(), project.getId());
    }

    // метод для удаления существующего проекта из бд
    public void deleteProject(Long id) {
        logger.info("Deleting project by ID: " + id);
        String sql = "DELETE FROM projects WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
