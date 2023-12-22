package com.demo.text_based_social_media.api.use_case.user;

import com.demo.text_based_social_media.api.role.application.RoleService;
import com.demo.text_based_social_media.api.role.domain.Role;
import com.demo.text_based_social_media.api.user.authentication.application.SignUpService;
import com.demo.text_based_social_media.api.user.authentication.domain.SignUpRequest;
import com.demo.text_based_social_media.api.user.details.adapter.out.UserAdapter;
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
public class SignUpUseCaseTest {

    @Autowired
    private SignUpService signUpService;

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
        SignUpRequest signUpRequest = new SignUpRequest("testuser@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest);
        assertNotNull(userAdapter.readUserByEmail("testuser@demo.com"));
    }

    @Test
    void shouldNotCreateUserWithInvalidEmail() {
        SignUpRequest signUpRequest = new SignUpRequest("testuserdemo.com", "12345678", "USER", false);
        Assertions.assertThrows(ResponseStatusException.class, () -> signUpService.signUpUser(signUpRequest));
    }

    @Test
    void shouldNotCreateExistingUser() {
        SignUpRequest signUpRequest = new SignUpRequest("testuser@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest);
        Assertions.assertThrows(ResponseStatusException.class, () -> signUpService.signUpUser(signUpRequest));
    }
}
