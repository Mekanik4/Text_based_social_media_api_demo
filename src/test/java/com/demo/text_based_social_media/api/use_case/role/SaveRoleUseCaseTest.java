package com.demo.text_based_social_media.api.use_case.role;


import com.demo.text_based_social_media.api.role.application.RoleService;
import com.demo.text_based_social_media.api.role.domain.Role;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class SaveRoleUseCaseTest {

    @Autowired
    private RoleService roleService;

    @Test
    void shouldSaveRole() {
        Role role = new Role();
        role.setName("ADMIN");
        roleService.saveRole(role);
        assertNotNull(roleService.getRoleByName("ADMIN"));
    }
}
