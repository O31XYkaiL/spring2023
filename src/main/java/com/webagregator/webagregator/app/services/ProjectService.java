package com.webagregator.webagregator.app.services;

import com.webagregator.webagregator.app.repositories.ProjectRepository;
import com.webagregator.webagregator.app.repositories.StudentRepository;
import com.webagregator.webagregator.domain.Project;
import com.webagregator.webagregator.domain.ProjectRole;
import com.webagregator.webagregator.domain.Student;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProjectService {
    /**
     * Репозиторий проекта
     */
    private final ProjectRepository projectRepository;
    /**
     * Репозиторий студента
     */
    private final StudentRepository studentRepository;

    /**
     * Конструктор сервиса.
     *
     * @param projectRepository Репозиторий проектов.
     * @param studentRepository Репозиторий студентов.
     */
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
     */
    public void createProject(Project project) {
        log.info("Creating a new project: {}", project);
        projectRepository.save(project);
    }
    /**
     * Обновить информацию о проекте.
     *
     * @param project Объект проекта для обновления.
     */
    public void updateProject(Project project) {
        log.info("Updating project: {}", project);
        projectRepository.save(project);
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
     * Сохраняет изображение проекта и обновляет ссылку в базе данных.
     *
     * @param projectId  Идентификатор проекта.
     * @param coverImage Изображение проекта в виде объекта MultipartFile.
     * @return Объект проекта с обновленной ссылкой на изображение.
     */
    public Project uploadProjectCoverImage(Long projectId, MultipartFile coverImage) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project != null && coverImage != null && !coverImage.isEmpty()) {
            try {
                String imageDirPath = "C:\\Users\\koziy\\IdeaProjects\\spring2023\\src\\images";
                String imageName = UUID.randomUUID() + ".jpg";
                Path imagePath = Path.of(imageDirPath, imageName);

                Files.createDirectories(Path.of(imageDirPath));
                Files.copy(coverImage.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

                project.setCoverImage(imagePath.toString());
                projectRepository.save(project);

                log.info("Uploaded cover image for project with ID: {}", projectId);
                return project;
            } catch (IOException e) {
                log.error("Error while uploading cover image: {}", e.getMessage());
            }
        }

        return null;
    }

    /**
     * Загружает видео геймплея проекта, сохраняет его в указанную директорию и обновляет ссылку в базе данных.
     *
     * @param projectId      Идентификатор проекта, для которого загружается видео геймплея.
     * @param gameplayVideo  Данные видео геймплея в виде объекта MultipartFile.
     * @return Объект проекта с обновленной ссылкой на видео геймплея.
     */
    public Project uploadGameplayVideo(Long projectId, MultipartFile gameplayVideo) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project != null && gameplayVideo != null && !gameplayVideo.isEmpty()) {
            try {
                String tempDirPath = "C:\\Users\\koziy\\IdeaProjects\\spring2023\\src\\videos";
                String tempVideoPath = tempDirPath + "\\" + UUID.randomUUID() + ".mp4";
                Files.createDirectories(Path.of(tempDirPath));

                Files.copy(gameplayVideo.getInputStream(), Path.of(tempVideoPath), StandardCopyOption.REPLACE_EXISTING);

                project.setGameplayVideo(tempVideoPath);
                projectRepository.save(project);

                log.info("Uploaded gameplay video for project with ID: {}", projectId);
                return project;
            } catch (IOException e) {
                log.error("Error while uploading gameplay video: {}", e.getMessage());
            }
        }

        return null;
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

        return projectRepository.findByCategoryAndSubcategory(category, subcategory);
    }

    /**
     * Загружает архив проекта, разархивирует его и сохраняет ссылку в базе данных.
     *
     * @param projectId   Идентификатор проекта, для которого загружается архив.
     * @param archiveFile Данные архива в виде объекта MultipartFile.
     * @return Объект проекта с обновленной ссылкой на разархивированную папку.
     */
    public Project uploadProjectArchive(Long projectId, MultipartFile archiveFile) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project != null && archiveFile != null && !archiveFile.isEmpty()) {
            try {
                String tempDirPath = "C:\\Users\\koziy\\IdeaProjects\\spring2023\\src\\archives";
                String tempExtractedDirPath = tempDirPath + "\\" + UUID.randomUUID();
                Files.createDirectories(Path.of(tempExtractedDirPath));

                String archiveFileName = StringUtils.cleanPath(archiveFile.getOriginalFilename());
                Path tempArchivePath = Path.of(tempDirPath, archiveFileName);
                Files.copy(archiveFile.getInputStream(), tempArchivePath, StandardCopyOption.REPLACE_EXISTING);

                unzipArchive(tempArchivePath, tempExtractedDirPath);

                project.setProjectArchivePath(tempExtractedDirPath);
                projectRepository.save(project);

                log.info("Uploaded and extracted archive for project with ID: {}", projectId);
                return project;
            } catch (IOException e) {
                log.error("Error while uploading and extracting project archive: {}", e.getMessage());
            }
        }

        return null;
    }

    /**
     * Разархивирует ZIP-архив в указанную папку.
     *
     * @param archivePath Путь к ZIP-архиву.
     * @param extractPath Путь к директории, в которую будет произведена разархивация.
     * @throws IOException В случае ошибок ввода-вывода.
     */
    public void unzipArchive(Path archivePath, String extractPath) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(archivePath))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                String entryName = entry.getName();
                Path entryPath = Paths.get(extractPath, entryName);

                // Создание директорий (если это директория) и запись файла
                if (entry.isDirectory()) {
                    Files.createDirectories(entryPath);
                } else {
                    Files.copy(zipInputStream, entryPath, StandardCopyOption.REPLACE_EXISTING);
                }

                zipInputStream.closeEntry();
            }
        }
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
