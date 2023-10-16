package com.webagregator.webagregator.app;

import com.webagregator.webagregator.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AdminService {
    private final JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    public AdminService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // метод для получения инфы об админе по его id
    public Admin getAdminById(Long id) {
        logger.info("Getting admin by ID: " + id);
        String sql = "SELECT * FROM admins WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Admin admin = new Admin();
            admin.setId(rs.getLong("id"));
            admin.setUsername(rs.getString("username"));
            admin.setPassword(rs.getString("password"));
            return admin;
        });
    }

    // метод для создания нового админа в бд
    public void createAdmin(Admin admin) {
        logger.info("Creating a new admin: " + admin);
        String sql = "INSERT INTO admins (username, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, admin.getUsername(), admin.getPassword());
    }

    // метод для обновления инфы об уже существующем админе
    public void updateAdmin(Admin admin) {
        logger.info("Updating admin: " + admin);
        String sql = "UPDATE admins SET username = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql, admin.getUsername(), admin.getPassword(), admin.getId());
    }

    // метод для удаления существующего админа из бд
    public void deleteAdmin(Long id) {
        logger.info("Deleting admin by ID: " + id);
        String sql = "DELETE FROM admins WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
