package com.demo.text_based_social_media.api.adapter.post;

import com.demo.text_based_social_media.api.follow.adapter.out.FollowAdapter;
import com.demo.text_based_social_media.api.follow.domain.Follow;
import com.demo.text_based_social_media.api.post.adapter.out.PostAdapter;
import com.demo.text_based_social_media.api.post.domain.Post;
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

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PostReadPortTest {

    @Autowired
    private PostAdapter postAdapter;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private UserAdapter userAdapter;

    @Autowired
    private FollowAdapter followAdapter;

    private Long userId;

    private Long postId;

    @BeforeEach
    public void setUp() {
        Role role = new Role();
        role.setName("USER");
        roleService.saveRole(role);
        SignUpRequest signUpRequest = new SignUpRequest("testuser@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest);
        userId = userAdapter.readUserByEmail(signUpRequest.getEmail()).getId();
        Post post = new Post(1L, "A new post", userId, null, null, now());
        postAdapter.save(post);
        postId = postAdapter.getAllPostsByUserId(userId).get(0).getId();
    }

    @Test
    void shouldGetPostById() {
        Post post = postAdapter.getPostById(postId);
        Assertions.assertNotNull(post);
        Assertions.assertEquals(post.getContext(), "A new post");
    }

    @Test
    void shouldThrowErrorInvalidId() {
        Assertions.assertThrows(ResponseStatusException.class, () -> postAdapter.getPostById(postId + 1));
    }

    @Test
    void shouldGetAllPostsFromUser() {
        List<Post> allPosts = postAdapter.getAllPostsByUserId(userId);
        assertNotNull(allPosts);
        assertEquals(allPosts.size(), 1);
        assertEquals(postAdapter.getAllPostsByUserId(userId).get(0).getContext(), "A new post");
        Post post = new Post(2L, "A new post", userId, null, null, now());
        postAdapter.save(post);
        List<Post> allPosts2 = postAdapter.getAllPostsByUserId(userId);
        assertNotNull(allPosts2);
        assertEquals(allPosts2.size(), 2);
    }

    @Test
    void shouldGetAllPostsByFollowing() {
        SignUpRequest signUpRequest2 = new SignUpRequest("testuser2@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest2);
        Long user2Id = userAdapter.readUserByEmail(signUpRequest2.getEmail()).getId();
        SignUpRequest signUpRequest3 = new SignUpRequest("testuser3@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest3);
        Long user3Id = userAdapter.readUserByEmail(signUpRequest3.getEmail()).getId();
        Follow follow = new Follow(null, user2Id, user3Id, null, null);
        followAdapter.save(follow);
        Follow follow2 = new Follow(null, user2Id, userId, null, null);
        followAdapter.save(follow2);
        Post post = new Post(3L, "A new post from user 3", user3Id, null, null, now());
        postAdapter.save(post);
        List<Long> followingIds = new ArrayList<>();
        followingIds.add(userId);
        followingIds.add(user3Id);
        List<Post> allPosts = postAdapter.getAllFollowingPosts(followingIds);
        Assertions.assertEquals(allPosts.size(), 2);
        allPosts.forEach(post1 -> {
            if (post1.getUserId().longValue() == userId)
                Assertions.assertEquals(post1.getContext(), "A new post");
            if (post1.getUserId().longValue() == user3Id)
                Assertions.assertEquals(post1.getContext(), "A new post from user 3");
        });
    }

    @Test
    void shouldGetAllPostIds() {
        Assertions.assertEquals(postAdapter.getPostIdsByUserId(userId).size(), 1);
        Post post = new Post(2L, "A new post2", userId, null, null, now());
        postAdapter.save(post);
        Assertions.assertEquals(postAdapter.getPostIdsByUserId(userId).size(), 2);
    }

    @Test
    void shouldGetAllPostIdsByFollowingIds() {
        SignUpRequest signUpRequest2 = new SignUpRequest("testuser2@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest2);
        Long user2Id = userAdapter.readUserByEmail(signUpRequest2.getEmail()).getId();
        SignUpRequest signUpRequest3 = new SignUpRequest("testuser3@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest3);
        Long user3Id = userAdapter.readUserByEmail(signUpRequest3.getEmail()).getId();
        Follow follow = new Follow(null, user2Id, user3Id, null, null);
        followAdapter.save(follow);
        Follow follow2 = new Follow(null, user2Id, userId, null, null);
        followAdapter.save(follow2);
        Post post = new Post(3L, "A new post from user 3", user3Id, null, null, now());
        postAdapter.save(post);
        List<Long> followingIds = new ArrayList<>();
        followingIds.add(userId);
        followingIds.add(user3Id);
        List<Long> allIds = postAdapter.getPostIdsByFollowingIds(followingIds);
        Assertions.assertEquals(allIds.size(), 2);
    }
}
