package com.demo.text_based_social_media.api.controller.comment;

import com.demo.text_based_social_media.api.post.application.PostService;
import com.demo.text_based_social_media.api.post.domain.Post;
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

import static java.time.LocalDateTime.now;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class CommentRestControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserAdapter userAdapter;

    private Long userId;

    private Long postId;
    private String token;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        Role role = new Role();
        role.setName("USER");
        roleService.saveRole(role);
        SignUpRequest signUpRequest = new SignUpRequest("testuser@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest);
        userId = userAdapter.readUserByEmail(signUpRequest.getEmail()).getId();
        token = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"testuser@demo.com\", \"password\": \"12345678\"}"))
                .andReturn().getResponse().getContentAsString();
        Post post = new Post(1L, "A new post", userId, null, null, now());
        postService.savePost(post);
        postId = postService.getAllPostsByUserId(userId).get(0).getId();
    }

    @Test
    void shouldCreateComment() throws Exception {
        mockMvc.perform(post("/users/me/posts/" + postId + "/comments/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", token.substring(10, token.length() - 2))
                        .content("{\"context\": \"This is a new comment\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetLatestComments() throws Exception {
        mockMvc.perform(get("/users/me/comments/latest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", token.substring(10, token.length() - 2)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetLatestCommentsOnAllPost() throws Exception {
        mockMvc.perform(get("/users/me/posts/comments/latest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", token.substring(10, token.length() - 2)))
                .andExpect(status().isOk());
    }
}
