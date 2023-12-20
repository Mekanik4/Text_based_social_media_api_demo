package com.demo.text_based_social_media.api.comment.application.port.in;

public interface ReadCommentUseCase {

    int countCommentsByUserIdAndPostId(Long userId, Long postId);
}
