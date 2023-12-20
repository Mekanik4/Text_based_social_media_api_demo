package com.demo.text_based_social_media.api.comment.application;

import com.demo.text_based_social_media.api.comment.application.port.in.ReadCommentUseCase;
import com.demo.text_based_social_media.api.comment.application.port.in.SaveCommentUseCase;
import com.demo.text_based_social_media.api.comment.application.port.out.CommentReadPort;
import com.demo.text_based_social_media.api.comment.application.port.out.CommentSavePort;
import com.demo.text_based_social_media.api.comment.domain.Comment;
import com.demo.text_based_social_media.api.post.application.port.out.PostReadPort;
import com.demo.text_based_social_media.api.user.authentication.application.port.out.ReadUserPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService implements ReadCommentUseCase, SaveCommentUseCase {

    private final CommentReadPort commentReadPort;

    private final CommentSavePort commentSavePort;

    private final ReadUserPort readUserPort;

    private final PostReadPort postReadPort;
    @Override
    public void saveComment(Comment comment) {
        if (commentReadPort.countCommentsByUserIdAndPostId(comment.getUserId(), comment.getPostId()) > 9)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You have reached the maximum number of comments in this post");
        comment.setPostedAt(LocalDateTime.now());
        comment.setUser(readUserPort.readUserById(comment.getUserId()));
        comment.setPost(postReadPort.getPostById(comment.getPostId()));
        commentSavePort.save(comment);
    }

    @Override
    public int countCommentsByUserIdAndPostId(Long userId, Long postId) {
        return commentReadPort.countCommentsByUserIdAndPostId(userId, postId);
    }
}
