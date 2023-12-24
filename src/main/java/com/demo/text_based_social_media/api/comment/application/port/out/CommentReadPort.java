package com.demo.text_based_social_media.api.comment.application.port.out;

import com.demo.text_based_social_media.api.comment.domain.Comment;

import java.util.List;

public interface CommentReadPort {

    int countCommentsByUserIdAndPostId(Long userId, Long postId);

    List<Comment> getCommentsByPostId(Long postId);

    Comment getLatestCommentByUserIdAndPostId(Long userId, Long postId);

    Comment getLatestCommentByPostId(Long postId);
}
