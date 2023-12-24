package com.demo.text_based_social_media.api.comment.application;

import com.demo.text_based_social_media.api.comment.application.port.in.ReadCommentUseCase;
import com.demo.text_based_social_media.api.comment.application.port.in.SaveCommentUseCase;
import com.demo.text_based_social_media.api.comment.application.port.out.CommentReadPort;
import com.demo.text_based_social_media.api.comment.application.port.out.CommentSavePort;
import com.demo.text_based_social_media.api.comment.domain.Comment;
import com.demo.text_based_social_media.api.follow.application.port.out.FollowReadPort;
import com.demo.text_based_social_media.api.post.application.port.out.PostReadPort;
import com.demo.text_based_social_media.api.post.domain.Post;
import com.demo.text_based_social_media.api.user.authentication.application.port.out.ReadUserPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService implements ReadCommentUseCase, SaveCommentUseCase {

    private final CommentReadPort commentReadPort;

    private final CommentSavePort commentSavePort;

    private final ReadUserPort readUserPort;

    private final PostReadPort postReadPort;

    private final FollowReadPort followReadPort;

    @Override
    public void saveComment(Comment comment) {
        if (commentReadPort.countCommentsByUserIdAndPostId(comment.getUserId(), comment.getPostId()) > 9)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have reached the maximum number of comments in this post");
        comment.setPostedAt(LocalDateTime.now());
        comment.setUser(readUserPort.readUserById(comment.getUserId()));
        comment.setPost(postReadPort.getPostById(comment.getPostId()));
        commentSavePort.save(comment);
    }

    @Override
    public int countCommentsByUserIdAndPostId(Long userId, Long postId) {
        return commentReadPort.countCommentsByUserIdAndPostId(userId, postId);
    }

    @Override
    public List<Comment> getLatestCommentsByUserId(Long userId) {
        List<Long> postIds = postReadPort.getPostIdsByFollowingIds(followReadPort.getFollowingIdsByFollowerId(userId));
        postIds.addAll(postReadPort.getPostIdsByUserId(userId));
        System.out.println(postIds);

        List<Comment> latestComments = new ArrayList<>();
        for (Long postId: postIds) {
            Comment comment = commentReadPort.getLatestCommentByUserIdAndPostId(userId, postId);
            if (comment != null) {
                Post post = postReadPort.getPostById(postId);
                post.setUser(readUserPort.readUserById(post.getUserId()));
                post.setContext(post.getContext().substring(0, 20).concat("..."));
                comment.setPost(post);
                latestComments.add(comment);
            }
        }
        return latestComments;
    }
}
