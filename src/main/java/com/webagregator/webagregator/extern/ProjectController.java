package com.webagregator.webagregator.extern;

import com.webagregator.webagregator.app.services.ProjectService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/projects")
public class ProjectController {
//    private final ProjectService projectService;
//
//    public ProjectController(ProjectService projectService) {
//        this.projectService = projectService;
//    }
//
//    /**
//     * Загружает архив проекта.
//     *
//     * @param projectId    ID проекта, в который загружается архив.
//     * @param archiveFile  Файл архива проекта в виде MultipartFile.
//     * @return ResponseEntity с сообщением о статусе загрузки архива.
//     */
//    @PostMapping("/{projectId}/uploadArchive")
//    public ResponseEntity<String> uploadProjectArchive(@PathVariable Long projectId, @RequestParam("archiveFile") MultipartFile archiveFile) {
//        if (archiveFile != null && !archiveFile.isEmpty()) {
//            try {
//                File convertedFile = convertMultipartFileToFile(archiveFile);
//
//                if (convertedFile != null) {
//                    Resource archiveResource = new FileSystemResource(convertedFile);
//                    projectService.uploadProjectArchive(projectId, archiveResource);
//                    return ResponseEntity.ok("Project archive uploaded successfully.");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return ResponseEntity.badRequest().body("Failed to upload project archive.");
//    }
//
//    /**
//     * Конвертирует MultipartFile в файл.
//     *
//     * @param multipartFile MultipartFile для преобразования.
//     * @return Результирующий файл, null если преобразование не удалось.
//     */
//    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
//        File file = new File(multipartFile.getOriginalFilename());
//        try (FileOutputStream fos = new FileOutputStream(file)) {
//            fos.write(multipartFile.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return file;
//    }
}