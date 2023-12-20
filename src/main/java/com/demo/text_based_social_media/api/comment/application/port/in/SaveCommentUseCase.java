package com.demo.text_based_social_media.api.comment.application.port.in;

import com.demo.text_based_social_media.api.comment.domain.Comment;

public interface SaveCommentUseCase {

    void saveComment(Comment comment);
}
