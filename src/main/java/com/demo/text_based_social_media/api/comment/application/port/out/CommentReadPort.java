package com.demo.text_based_social_media.api.comment.application.port.out;

public interface CommentReadPort {

    int countCommentsByUserIdAndPostId(Long userId, Long postId);
}
