package com.demo.text_based_social_media.api.adapter.comment;

import com.demo.text_based_social_media.api.comment.adapter.out.CommentAdapter;
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

import java.util.List;

import static java.time.LocalDateTime.now;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CommentReadPortTest {

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
        Comment comment = new Comment(1L, "A comment", userId, postId, null, null, now());
        commentAdapter.save(comment);
    }

    @Test
    void shouldCountCommentsByUserIdAndPostId() {
        Assertions.assertEquals(commentAdapter.countCommentsByUserIdAndPostId(userId, postId), 1);
        Comment comment = new Comment(2L, "A new comment", userId, postId, null, null, now());
        commentAdapter.save(comment);
        Assertions.assertEquals(commentAdapter.countCommentsByUserIdAndPostId(userId, postId), 2);
    }

    @Test
    void shouldGetAllCommentsFromPost() {
        Assertions.assertEquals(commentAdapter.getCommentsByPostId(postId).get(0).getContext(), "A comment");
        Comment comment = new Comment(2L, "A new comment", userId, postId, null, null, now());
        commentAdapter.save(comment);
        List<Comment> allComments = commentAdapter.getCommentsByPostId(postId);
        Assertions.assertEquals(allComments.size(), 2);
    }

    @Test
    void shouldGetLatestCommentOnPostFromUser() {
        Comment comment = new Comment(2L, "A new comment", userId, postId, null, null, now());
        commentAdapter.save(comment);
        Assertions.assertEquals(commentAdapter.getLatestCommentByUserIdAndPostId(userId, postId).getContext(), comment.getContext());
    }
}
