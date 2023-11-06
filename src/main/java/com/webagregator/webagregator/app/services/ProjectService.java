package com.webagregator.webagregator.app.services;

import com.webagregator.webagregator.app.repositories.ProjectRepository;
import com.webagregator.webagregator.app.repositories.StudentRepository;
import com.webagregator.webagregator.domain.Project;
import com.webagregator.webagregator.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, StudentRepository studentRepository) {
        this.projectRepository = projectRepository;
        this.studentRepository = studentRepository;
    }
    /**
     * Получить проект по ID.
     *
     * @param id ID проекта.
     * @return Объект проекта или null, если проект не найден.
     */
    public Project getProjectById(Long id) {
        log.info("Getting project by ID: {}", id);
        return projectRepository.findById(id).orElse(null);
    }
    /**
     * Создать новый проект.
     *
     * @param project Объект проекта для создания.
     * @return Созданный объект проекта.
     */
    public Project createProject(Project project) {
        log.info("Creating a new project: {}", project);
        projectRepository.save(project);
        return project;
    }
    /**
     * Обновить информацию о проекте.
     *
     * @param project Объект проекта для обновления.
     * @return Обновленный объект проекта.
     */
    public Project updateProject(Project project) {
        log.info("Updating project: {}", project);
        projectRepository.save(project);
        return project;
    }
    /**
     * Удалить проект по ID.
     *
     * @param id ID проекта для удаления.
     */
    public void deleteProject(Long id) {
        log.info("Deleting project by ID: {}", id);
        projectRepository.deleteById(id);
    }
    /**
     * Увеличить количество голосов у проекта.
     *
     * @param projectId ID проекта.
     * @param voteCount Количество голосов для увеличения.
     */
    public void increaseVoteCount(Long projectId, int voteCount) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project != null) {
            int updatedVoteCount = project.getVoteCount() + voteCount;
            project.setVoteCount(updatedVoteCount);
            projectRepository.save(project);
        }
    }
    /**
     * Редактировать проект, если студент является тимлидом и это его проект.
     *
     * @param studentId ID студента.
     * @param projectId ID проекта.
     * @param updatedProject Обновленные данные проекта.
     * @return Обновленный объект проекта или null, если редактирование не выполнено.
     */
    public Project editProjectByTeamLeader(Long studentId, Long projectId, Project updatedProject) {
        Student student = studentRepository.findById(studentId).orElse(null);
        Project project = projectRepository.findById(projectId).orElse(null);

        if (student == null || project == null || !student.equals(project.getCreator())) {
            return null;
        }

        if (!"Team Leader".equalsIgnoreCase(student.getRoleInProject())) {
            return null;
        }

        project.setProjectName(updatedProject.getProjectName());
        project.setProjectDescription(updatedProject.getProjectDescription());

        projectRepository.save(project);
        return project;
    }

    /**
     * Метод, который позволит нам получить категорию и подкатегорию проекта
     *
     * @param category Категория проекта.
     * @param subcategory Подкатегория проекта.
     * @return Список проектов, соответствующих заданным категории и подкатегории.
     */
    public List<Project> filterProjectsByCategoryAndSubcategory(String category, String subcategory) {
        log.info("Filtering projects by category and subcategory: {}, {}", category, subcategory);
        return projectRepository.findByCategoryAndSubcategory(category, subcategory);
    }

    /**
     * Загрузить архив проекта в базу данных.
     *
     * @param projectId ID проекта, в который загружается архив.
     * @param archiveData Данные архива в виде массива байт.
     * @return Обновленный объект проекта или null в случае ошибки.
     */
    public Project uploadProjectArchive(Long projectId, byte[] archiveData) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project != null) {
            try {
                project.setProjectArchive(archiveData);
                projectRepository.save(project);
                log.info("Uploaded archive for project with ID: {}", projectId);
                return project;
            } catch (Exception e) {
                log.error("Error while uploading project archive: {}", e.getMessage());
            }
        }

        return null;
    }
}
