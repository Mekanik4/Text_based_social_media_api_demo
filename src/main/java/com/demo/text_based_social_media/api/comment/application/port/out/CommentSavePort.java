package com.demo.text_based_social_media.api.comment.application.port.out;

import com.demo.text_based_social_media.api.comment.domain.Comment;

public interface CommentSavePort {

    void save(Comment comment);
}
