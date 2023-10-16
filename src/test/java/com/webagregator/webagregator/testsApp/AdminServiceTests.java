package com.webagregator.webagregator.testsApp;

import com.webagregator.webagregator.app.AdminService;
import com.webagregator.webagregator.domain.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdminServiceTests {

    @MockBean
    private JdbcTemplate jdbcTemplate;

    private AdminService adminService;

    @BeforeEach
    void setUp() {
        adminService = new AdminService(jdbcTemplate);
    }

    @Test
    public void testGetAdminById() {
        // Arrange
        Long adminId = 1L;
        Admin expectedAdmin = new Admin();
        expectedAdmin.setId(adminId);
        expectedAdmin.setUsername("TestAdmin");

        // Mocking jdbcTemplate behavior
        when(jdbcTemplate.queryForObject(anyString(), eq(Admin.class), any(Object[].class))).thenReturn(expectedAdmin);

        // Act
        Admin actualAdmin = adminService.getAdminById(adminId);

        // Assert
        assertEquals(expectedAdmin, actualAdmin);
    }

    @Test
    public void testCreateAdmin() {
        // Arrange
        Admin adminToCreate = new Admin();
        adminToCreate.setUsername("NewAdmin");

        // Act
        adminService.createAdmin(adminToCreate);

        // Assert
        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }

    @Test
    public void testUpdateAdmin() {
        // Arrange
        Admin adminToUpdate = new Admin();
        adminToUpdate.setId(1L);
        adminToUpdate.setUsername("UpdatedAdmin");

        // Act
        adminService.updateAdmin(adminToUpdate);

        // Assert
        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }

    @Test
    public void testDeleteAdmin() {
        // Arrange
        Long adminId = 1L;

        // Act
        adminService.deleteAdmin(adminId);

        // Assert
        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }
}
