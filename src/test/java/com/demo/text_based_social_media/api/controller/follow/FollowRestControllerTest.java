package com.demo.text_based_social_media.api.controller.follow;

import com.demo.text_based_social_media.api.follow.adapter.in.dto.FollowDto;
import com.demo.text_based_social_media.api.follow.application.FollowService;
import com.demo.text_based_social_media.api.role.application.RoleService;
import com.demo.text_based_social_media.api.role.domain.Role;
import com.demo.text_based_social_media.api.user.authentication.application.SignUpService;
import com.demo.text_based_social_media.api.user.authentication.domain.SignUpRequest;
import com.demo.text_based_social_media.api.user.details.adapter.out.UserAdapter;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class FollowRestControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private FollowService followService;

    @Autowired
    private UserAdapter userAdapter;

    private String token;

    private Long followerId;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        Role role = new Role();
        role.setName("USER");
        roleService.saveRole(role);
        SignUpRequest signUpRequest = new SignUpRequest("follower@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest);
        followerId = userAdapter.readUserByEmail(signUpRequest.getEmail()).getId();
        token = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"follower@demo.com\", \"password\": \"12345678\"}"))
                .andReturn().getResponse().getContentAsString();
        SignUpRequest signUpRequest2 = new SignUpRequest("following@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest2);
    }

    @Test
    void shouldFollowUser() throws Exception {
        mockMvc.perform(post("/users/me/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", token.substring(10, token.length() - 2))
                        .content("{\"followingName\": \"following@demo.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteFollow() throws Exception {
        FollowDto followDto = new FollowDto("following@demo.com");
        followService.saveFollow(followDto, followerId);
        mockMvc.perform(delete("/users/me/followers/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", token.substring(10, token.length() - 2))
                        .content("{\"followingName\": \"following@demo.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAllFollowing() throws Exception {
        mockMvc.perform(get("/users/me/following")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", token.substring(10, token.length() - 2)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAllFollowers() throws Exception {
        mockMvc.perform(get("/users/me/followers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", token.substring(10, token.length() - 2)))
                .andExpect(status().isOk());
    }
}
