package com.demo.text_based_social_media.api.adapter.role;

import com.demo.text_based_social_media.api.role.adapter.out.RoleAdapter;
import com.demo.text_based_social_media.api.role.domain.Role;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class RoleReadPortTest {

    @Autowired
    private RoleAdapter roleAdapter;

    private Role role;

    @BeforeEach
    public void setUp() {
        role = new Role();
        role.setName("ADMIN");
        roleAdapter.save(role);
    }

    @Test
    void shouldGetByName() {
        Assertions.assertNotNull(roleAdapter.getRoleByName(role.getName()));
        Assertions.assertEquals(roleAdapter.getRoleByName(role.getName()).getName(), "ADMIN");
    }

    @Test
    void shouldNotGetRoleByInvalidName() {
        Assertions.assertThrows(ResponseStatusException.class, () -> roleAdapter.getRoleByName("User"));
    }

    @Test
    void checkForRoleWithValidName() {
        Assertions.assertTrue(roleAdapter.existsByName(role.getName()));
    }

    @Test
    void checkForRoleWithInValidName() {
        Assertions.assertFalse(roleAdapter.existsByName("invalidName"));
    }
}
