package com.demo.text_based_social_media.api.use_case.user;


import com.demo.text_based_social_media.api.role.application.RoleService;
import com.demo.text_based_social_media.api.role.domain.Role;
import com.demo.text_based_social_media.api.user.authentication.application.SignUpService;
import com.demo.text_based_social_media.api.user.authentication.domain.SignUpRequest;
import com.demo.text_based_social_media.api.user.details.adapter.out.UserAdapter;
import com.demo.text_based_social_media.api.user.details.application.UserService;
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
public class ViewUserUseCaseTest {

    @Autowired
    private UserService userService;

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserAdapter userAdapter;

    private Long userId;
    @BeforeEach
    public void setUp() {
        Role role = new Role();
        role.setName("USER");
        roleService.saveRole(role);
        SignUpRequest signUpRequest = new SignUpRequest("testuser@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest);
        userId = userAdapter.readUserByEmail("testuser@demo.com").getId();
    }

    @Test
    void shouldGetLoggedInUserById() {
        Assertions.assertNotNull(userService.getUserById(userId));
    }

    @Test
    void shouldThrowErrorForWrongUserId() {
        Assertions.assertThrows(ResponseStatusException.class, () -> userService.getUserById(userId+1));
    }
}
