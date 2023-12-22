package com.demo.text_based_social_media.api.use_case.user;


import com.demo.text_based_social_media.api.role.application.RoleService;
import com.demo.text_based_social_media.api.role.domain.Role;
import com.demo.text_based_social_media.api.user.authentication.application.LoginService;
import com.demo.text_based_social_media.api.user.authentication.application.SignUpService;
import com.demo.text_based_social_media.api.user.authentication.domain.LoginRequest;
import com.demo.text_based_social_media.api.user.authentication.domain.SignUpRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseCookie;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class LoginUseCaseTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private RoleService roleService;

    @BeforeEach
    public void setUp() {
        Role role = new Role();
        role.setName("USER");
        roleService.saveRole(role);
        SignUpRequest signUpRequest = new SignUpRequest("testuser@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest);
    }

    @Test
    void shouldLoginUser() {
        LoginRequest loginRequest = new LoginRequest("testuser@demo.com", "12345678");
        ResponseCookie responseCookie = loginService.loginUser(loginRequest);
        Assertions.assertNotNull(responseCookie);
    }

    @Test
    void shouldNotLoginUnregisteredUser() {
        LoginRequest loginRequest = new LoginRequest("test@demo.com", "12345678");
        Assertions.assertThrows(ResponseStatusException.class, () -> loginService.loginUser(loginRequest));
    }

    @Test
    void shouldNotLoginUserWithWrongPassword() {
        LoginRequest loginRequest = new LoginRequest("testuser@demo.com", "12345679");
        Assertions.assertThrows(ResponseStatusException.class, () -> loginService.loginUser(loginRequest));
    }
}
