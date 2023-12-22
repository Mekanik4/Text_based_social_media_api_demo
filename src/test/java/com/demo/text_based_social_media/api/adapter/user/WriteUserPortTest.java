package com.demo.text_based_social_media.api.adapter.user;

import com.demo.text_based_social_media.api.role.application.RoleService;
import com.demo.text_based_social_media.api.role.domain.Role;
import com.demo.text_based_social_media.api.user.details.adapter.out.UserAdapter;
import com.demo.text_based_social_media.api.user.details.domain.UserCreateRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static java.time.LocalDateTime.now;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class WriteUserPortTest {

    @Autowired
    private UserAdapter userAdapter;

    @Autowired
    private RoleService roleService;

    @BeforeEach
    public void setUp() {
        Role role = new Role();
        role.setName("USER");
        roleService.saveRole(role);
    }

    @Test
    void shouldCreateUser() {
        UserCreateRequest userCreateRequest = new UserCreateRequest("testuser@demo.com", "12345678", "USER", now(), false);
        userAdapter.create(userCreateRequest);
        Assertions.assertNotNull(userAdapter.readUserByEmail("testuser@demo.com"));
    }
}
