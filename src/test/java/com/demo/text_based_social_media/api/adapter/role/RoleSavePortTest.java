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

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class RoleSavePortTest {

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
    void shouldSaveRole() {
        assertNotNull(roleAdapter.getRoleByName("ADMIN"));
    }

    @Test
    void shouldThrowErrorFromDB() {
        Assertions.assertThrows(RuntimeException.class, () -> roleAdapter.save(role));
    }
}
