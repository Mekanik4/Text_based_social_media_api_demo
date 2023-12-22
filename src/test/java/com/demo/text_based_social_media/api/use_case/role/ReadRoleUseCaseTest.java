package com.demo.text_based_social_media.api.use_case.role;


import com.demo.text_based_social_media.api.role.application.RoleService;
import com.demo.text_based_social_media.api.role.domain.Role;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ReadRoleUseCaseTest {

    @Autowired
    private RoleService roleService;

    @BeforeEach
    public void setUp() {
        Role role = new Role();
        role.setName("ADMIN");
        roleService.saveRole(role);
    }
    @Test
    void shouldReadRoleByName() {
        assertNotNull(roleService.getRoleByName("ADMIN"));
    }

    @Test
    void shouldNotReadRoleWithWrongName() {
        Assertions.assertThrows(ResponseStatusException.class, () -> roleService.getRoleByName("USER"));
    }
}
