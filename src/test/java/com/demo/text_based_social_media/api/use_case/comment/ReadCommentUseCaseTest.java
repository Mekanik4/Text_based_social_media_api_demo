package com.demo.text_based_social_media.api.use_case.comment;


import com.demo.text_based_social_media.api.comment.application.CommentService;
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

import java.util.List;

import static java.time.LocalDateTime.now;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ReadCommentUseCaseTest {

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
        postService.savePost(post);
        postId = postService.getAllPostsByUserId(userId).get(0).getId();
        Comment comment = new Comment(1L, "A new comment", userId, postId, null, null, now());
        commentService.saveComment(comment);
    }

    @Test
    void shouldCountCommentsByUserIdAndPostId() {
        Assertions.assertEquals(commentService.countCommentsByUserIdAndPostId(userId, postId), 1);
        Comment comment = new Comment(2L, "A new comment", userId, postId, null, null, now());
        commentService.saveComment(comment);
        Assertions.assertEquals(commentService.countCommentsByUserIdAndPostId(userId, postId), 2);
    }

    @Test
    void shouldGetLatestCommentsByUserId() {
        SignUpRequest signUpRequest = new SignUpRequest("testuser2@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest);
        Long followingId = userAdapter.readUserByEmail(signUpRequest.getEmail()).getId();
        followAdapter.save(new Follow(1L, userId, followingId, null, null));
        Post post = new Post(2L, "A new post from following", followingId, null, null, now());
        postService.savePost(post);
        Long followingPostId = postId = postService.getAllPostsByUserId(followingId).get(0).getId();
        Comment comment = new Comment(2L, "A new comment to following post", userId, followingPostId, null, null, now());
        commentService.saveComment(comment);
        Assertions.assertEquals(commentService.getLatestCommentsByUserId(userId).size(), 2);
        Comment newComment = new Comment(3L, "A new comment to user's post", userId, postId, null, null, now());
        commentService.saveComment(newComment);
        List<Comment> latestComments = commentService.getLatestCommentsByUserId(userId);
        Assertions.assertEquals(latestComments.size(), 2);
        latestComments.forEach(comment1 -> {
                    if (comment1.getPostId().longValue() == postId)
                        Assertions.assertEquals(comment1.getContext(), "A new comment to user's post");
                    if (comment1.getUserId().longValue() == followingPostId)
                        Assertions.assertEquals(comment1.getContext(), "A new comment to following post");
                });


    }
}
