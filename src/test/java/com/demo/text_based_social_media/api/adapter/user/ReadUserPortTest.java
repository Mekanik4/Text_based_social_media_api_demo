package com.demo.text_based_social_media.api.adapter.user;

import com.demo.text_based_social_media.api.role.application.RoleService;
import com.demo.text_based_social_media.api.role.domain.Role;
import com.demo.text_based_social_media.api.user.details.adapter.out.UserAdapter;
import com.demo.text_based_social_media.api.user.details.domain.User;
import com.demo.text_based_social_media.api.user.details.domain.UserCreateRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import static java.time.LocalDateTime.now;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ReadUserPortTest {

    @Autowired
    private UserAdapter userAdapter;

    @Autowired
    private RoleService roleService;

    private Long userId;

    @BeforeEach
    public void setUp() {
        Role role = new Role();
        role.setName("USER");
        roleService.saveRole(role);
        UserCreateRequest userCreateRequest = new UserCreateRequest("testuser@demo.com", "12345678", "USER", now(), false);
        userAdapter.create(userCreateRequest);
        userId = userAdapter.readUserByEmail("testuser@demo.com").getId();
    }

    @Test
    void shouldGetUserById() {
        User user = userAdapter.readUserById(userId);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getEmail(), "testuser@demo.com");
    }

    @Test
    void shouldThrowErrorForInvalidId() {
        Assertions.assertThrows(ResponseStatusException.class, () -> userAdapter.readUserById(userId + 1));
    }

    @Test
    void shouldGetUserByEmail() {
        User user = userAdapter.readUserByEmail("testuser@demo.com");
        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getId(), userId);
    }

    @Test
    void shouldThrowErrorForInvalidEmail() {
        Assertions.assertThrows(ResponseStatusException.class, () -> userAdapter.readUserByEmail("InvalidEmail@demo.com"));
    }

    @Test
    void checkIfValidEmailExists() {
        Assertions.assertTrue(userAdapter.existsByEmail("testuser@demo.com"));
    }

    @Test
    void checkIfInValidEmailExists() {
        Assertions.assertFalse(userAdapter.existsByEmail("InvalidEmail@demo.com"));
    }
}
