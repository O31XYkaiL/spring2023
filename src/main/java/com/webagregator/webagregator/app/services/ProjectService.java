package com.webagregator.webagregator.app.services;

import com.webagregator.webagregator.app.repositories.ProjectRepository;
import com.webagregator.webagregator.app.repositories.StudentRepository;
import com.webagregator.webagregator.domain.Project;
import com.webagregator.webagregator.domain.ProjectRole;
import com.webagregator.webagregator.domain.Student;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Service
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final StudentRepository studentRepository;

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

        if (ProjectRole.TEAM_LEAD.getRole().equalsIgnoreCase(student.getRoleInProject().getRole())) {
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

        List<Project> projects = projectRepository.findByCategoryAndSubcategory(category, subcategory);

        return projects;
    }
    /**
     * Загружает архив проекта в базу данных.
     *
     * @param projectId       Идентификатор проекта, для которого загружается архив.
     * @param archiveResource Ресурс архива проекта.
     * @return Объект проекта с обновленным архивом, либо null в случае ошибки загрузки.
     */
    public Project uploadProjectArchive(Long projectId, Resource archiveResource) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project != null) {
            try {
                if (archiveResource.contentLength() > 0) {
                    project.setProjectArchive(archiveResource);

                    projectRepository.save(project);
                    log.info("Uploaded archive for project with ID: {}", projectId);
                    return project;
                }
            } catch (Exception e) {
                log.error("Error while uploading project archive: {}", e.getMessage());
            }
        }

        return null;
    }

    /**
     * Осуществляет голосование студента за определенный проект.
     * @param studentId ID студента, голосующего за проект.
     * @param projectId ID проекта, за который проходит голосование.
     * @return {@code true}, если голосование выполнено успешно; в противном случае - {@code false}.
     */
    public boolean voteForProject(Long studentId, Long projectId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        Project project = projectRepository.findById(projectId).orElse(null);

        if (student != null && project != null && student.getVoteCount() >= 5) {
            if (student.getVotedProjects().size() < 3 && !student.getVotedProjects().contains(project)) {
                project.setVoteCount(project.getVoteCount() + 5);
                student.setVoteCount(student.getVoteCount() - 5);

                List<Project> projectVotes = student.getVotedProjects();
                projectVotes.add(project);
                student.setVotedProjects(projectVotes);
                studentRepository.save(student);

                List<Student> voters = project.getVoters();
                voters.add(student);
                project.setVoters(voters);
                projectRepository.save(project);

                return true;
            }
        }
        return false;
    }
}
