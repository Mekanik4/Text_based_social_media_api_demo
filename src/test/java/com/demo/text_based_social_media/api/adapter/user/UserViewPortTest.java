package com.demo.text_based_social_media.api.adapter.user;


import com.demo.text_based_social_media.api.role.application.RoleService;
import com.demo.text_based_social_media.api.role.domain.Role;
import com.demo.text_based_social_media.api.user.details.adapter.out.UserAdapter;
import com.demo.text_based_social_media.api.user.details.adapter.out.UserViewAdapter;
import com.demo.text_based_social_media.api.user.details.domain.UserCreateRequest;
import com.demo.text_based_social_media.api.user.details.domain.UserView;
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
public class UserViewPortTest {

    @Autowired
    private UserViewAdapter userViewAdapter;

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
        UserView user = userViewAdapter.getUserById(userId);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getEmail(), "testuser@demo.com");
    }

    @Test
    void shouldThrowErrorForInvalidId() {
        Assertions.assertThrows(ResponseStatusException.class, () -> userViewAdapter.getUserById(userId + 1));
    }
}
