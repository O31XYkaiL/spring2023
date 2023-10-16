package com.webagregator.webagregator.testsDomain;

import com.webagregator.webagregator.domain.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminTest {

    private Admin admin;

    @BeforeEach
    void setUp() {
        admin = new Admin();
    }

    @Test
    void testAdminSetterGetter() {
        admin.setUsername("adminuser");
        admin.setPassword("adminpass");

        assertEquals("adminuser", admin.getUsername());
        assertEquals("adminpass", admin.getPassword());
    }
}
