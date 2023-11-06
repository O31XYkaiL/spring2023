package com.webagregator.webagregator.extern;

import com.webagregator.webagregator.domain.Project;
import com.webagregator.webagregator.app.services.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {
//    @PostMapping("/{projectId}/uploadArchive")
//    public ResponseEntity<String> uploadProjectArchive(@PathVariable Long projectId, @RequestParam("archiveFile") MultipartFile archiveFile) {
//        if (archiveFile != null) {
//            try {
//                File convertedFile = convertMultipartFileToFile(archiveFile);
//
//                if (convertedFile != null) {
//                    Project uploadedProject = projectService.uploadProjectArchive(projectId, convertedFile);
//                    if (uploadedProject != null) {
//                        return ResponseEntity.ok("Project archive uploaded successfully.");
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return ResponseEntity.badRequest().body("Failed to upload project archive.");
//    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }
}

