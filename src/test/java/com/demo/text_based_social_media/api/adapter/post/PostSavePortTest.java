package com.demo.text_based_social_media.api.adapter.post;


import com.demo.text_based_social_media.api.post.adapter.out.PostAdapter;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PostSavePortTest {

    @Autowired
    private PostAdapter postAdapter;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SignUpService signUpService;

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
        userId = userAdapter.readUserByEmail(signUpRequest.getEmail()).getId();
    }

    @Test
    void shouldSavePost() {
        Post post = new Post(1L, "A new post", userId, null, null, now());
        postAdapter.save(post);
        List<Post> allPosts = postAdapter.getAllPostsByUserId(userId);
        assertEquals(allPosts.size(), 1);
        assertEquals(allPosts.get(0).getContext(), "A new post");
    }
}
