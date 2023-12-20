package com.demo.text_based_social_media.api.post.application.port.in;

import com.demo.text_based_social_media.api.post.domain.Post;

public interface ReadPostUseCase {
    Post getPostById(Long id);
}
