package com.demo.text_based_social_media.api.comment.application.port.in;

import com.demo.text_based_social_media.api.comment.domain.Comment;

import java.util.List;

public interface ReadCommentUseCase {

    int countCommentsByUserIdAndPostId(Long userId, Long postId);

    List<Comment> getLatestCommentsByUserId(Long userId);

    List<Comment> getLatestComments(Long userId);
}
