package com.demo.text_based_social_media.api.use_case.post;

import com.demo.text_based_social_media.api.comment.adapter.out.CommentAdapter;
import com.demo.text_based_social_media.api.comment.domain.Comment;
import com.demo.text_based_social_media.api.follow.adapter.out.FollowAdapter;
import com.demo.text_based_social_media.api.follow.domain.Follow;
import com.demo.text_based_social_media.api.post.application.PostService;
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

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ReadPostUseCaseTest {

    @Autowired
    private PostService postService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private UserAdapter userAdapter;

    @Autowired
    private FollowAdapter followAdapter;

    @Autowired
    private CommentAdapter commentAdapter;

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
        postService.savePost(post);
        postId = postService.getAllPostsByUserId(userId).get(0).getId();
        Comment comment = new Comment(1L, "A new comment", userId, postId, null, null, now());
        commentAdapter.save(comment);
    }

    @Test
    void shouldReadPostById() {
        assertNotNull(postService.getPostById(postId));
        assertEquals(postService.getPostById(postId).getContext(), "A new post");
    }

    @Test
    void shouldThrowExceptionForInvalidPostId() {
        Assertions.assertThrows(ResponseStatusException.class, () -> postService.getPostById(postId + 1));
    }

    @Test
    void shouldReadAllPostsFromUser() {
        List<Post> allPosts = postService.getAllPostsByUserId(userId);
        assertNotNull(allPosts);
        assertEquals(allPosts.get(0).getContext(), "A new post");
        assertNotNull(allPosts.get(0).getComments());
    }

    @Test
    void shouldReadAllFollowingPosts() {
        SignUpRequest signUpRequest = new SignUpRequest("testuser2@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest);
        Long followingId = userAdapter.readUserByEmail(signUpRequest.getEmail()).getId();
        followAdapter.save(new Follow(1L, userId, followingId, null, null));
        Post post = new Post(2L, "A new post from a follower", followingId, null, null, now());
        postService.savePost(post);
        assertNotNull(postService.getAllFollowingPost(userId));
        assertEquals(postService.getAllFollowingPost(userId).get(0).getContext(), "A new post from a follower");
    }
}
