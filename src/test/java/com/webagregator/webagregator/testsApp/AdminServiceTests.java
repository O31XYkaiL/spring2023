package com.webagregator.webagregator.testsApp;

import com.webagregator.webagregator.app.repositories.AdminRepository;
import com.webagregator.webagregator.app.services.AdminService;
import com.webagregator.webagregator.domain.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AdminServiceTests {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAdminById() {
        Long adminId = 1L;
        Admin admin = new Admin();
        admin.setId(adminId);

        Mockito.when(adminRepository.findById(adminId)).thenReturn(java.util.Optional.of(admin));

        Admin result = adminService.getAdminById(adminId);

        assertNotNull(result);
        assertEquals(adminId, result.getId());
    }

    @Test
    public void testGetAdminById_WhenAdminNotFound() {
        Long adminId = 1L;

        Mockito.when(adminRepository.findById(adminId)).thenReturn(java.util.Optional.empty());

        Admin result = adminService.getAdminById(adminId);

        assertNull(result);
    }

    @Test
    public void testCreateAdmin() {
        Admin admin = new Admin();

        adminService.createAdmin(admin);

        Mockito.verify(adminRepository).save(admin);
    }

    @Test
    public void testUpdateAdmin() {
        Admin admin = new Admin();

        adminService.updateAdmin(admin);

        Mockito.verify(adminRepository).save(admin);
    }

    @Test
    public void testDeleteAdmin() {
        Long adminId = 1L;

        adminService.deleteAdmin(adminId);

        Mockito.verify(adminRepository).deleteById(adminId);
    }
}
