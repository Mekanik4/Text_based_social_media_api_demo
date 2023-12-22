package com.demo.text_based_social_media.api.use_case.comment;


import com.demo.text_based_social_media.api.comment.adapter.out.CommentAdapter;
import com.demo.text_based_social_media.api.comment.application.CommentService;
import com.demo.text_based_social_media.api.comment.domain.Comment;
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

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class SaveCommentUseCaseTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private UserAdapter userAdapter;

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
    }

    @Test
    void shouldSaveValidComment() {
        Comment comment = new Comment(1L, "A new comment", userId, postId, null, null, now());
        commentService.saveComment(comment);
        assertNotNull(commentAdapter.getLatestCommentByUserIdAndPostId(userId, postId));
        assertEquals(commentAdapter.getLatestCommentByUserIdAndPostId(userId, postId).getContext(), "A new comment");
    }

    @Test
    void shouldNotHaveMoreCommentsByUser() {
        Comment comment = new Comment(null, "A new comment", userId, postId, null, null, now());
        for (int i = 0; i < 10; i++)
            commentService.saveComment(comment);
        Assertions.assertThrows(ResponseStatusException.class, () -> commentService.saveComment(comment));
    }
}
