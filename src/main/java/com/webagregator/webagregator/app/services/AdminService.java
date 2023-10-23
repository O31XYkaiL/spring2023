package com.webagregator.webagregator.app.services;

import com.webagregator.webagregator.app.repositories.AdminRepository;
import com.webagregator.webagregator.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin getAdminById(Long id) {
        log.info("Getting admin by ID: {}", id);
        return adminRepository.findById(id).orElse(null);
    }

    public void createAdmin(Admin admin) {
        log.info("Creating a new admin: {}", admin);
        adminRepository.save(admin);
    }

    public void updateAdmin(Admin admin) {
        log.info("Updating admin: {}", admin);
        adminRepository.save(admin);
    }

    public void deleteAdmin(Long id) {
        log.info("Deleting admin by ID: {}", id);
        adminRepository.deleteById(id);
    }
}
