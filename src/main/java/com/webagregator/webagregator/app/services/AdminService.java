package com.webagregator.webagregator.app.services;

import com.webagregator.webagregator.app.repositories.AdminRepository;
import com.webagregator.webagregator.domain.Admin;
import com.webagregator.webagregator.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    /**
     * Получить админа по ID.
     *
     * @param id ID админа.
     * @return Объект админа или null, если админ не найден.
     */
    public Admin getAdminById(Long id) {
        log.info("Getting admin by ID: {}", id);
        return adminRepository.findById(id).orElse(null);
    }
    /**
     * Создать нового админа.
     *
     * @param admin Объект админа для создания.
     */
    public void createAdmin(Admin admin) {
        log.info("Creating a new admin: {}", admin);
        adminRepository.save(admin);
    }
    /**
     * Обновить инфу об админе.
     *
     * @param admin Объект админа для обновления.
     */
    public void updateAdmin(Admin admin) {
        log.info("Updating admin: {}", admin);
        adminRepository.save(admin);
    }
    /**
     * Удалить админа.
     *
     * @param id ID админа для удаления.
     */
    public void deleteAdmin(Long id) {
        log.info("Deleting admin by ID: {}", id);
        adminRepository.deleteById(id);
    }
    /**
     * Получить список проектов, поступающих на проверку админу.
     *
     * @param adminId ID админа, для которого необходимо получить проекты на проверку.
     * @return Список проектов, ожидающих проверку.
     */
    public List<Project> getProjectsForReview(Long adminId) {
        log.info("Getting projects for review by Admin ID: {}", adminId);
        // тут должна быть бизнес-логика отправки проекта на ревью, но я ее еще не придумала ыыы
        return null;
    }
}
