package com.demo.text_based_social_media.api.post.application.port.out;

import com.demo.text_based_social_media.api.post.domain.Post;

public interface PostSavePort {

    void save(Post post);
}
