package com.demo.text_based_social_media.api.post.application.port.in;

import com.demo.text_based_social_media.api.post.domain.Post;

import java.util.List;

public interface ReadPostUseCase {
    Post getPostById(Long id);

    List<Post> getAllPostsByUserId(Long userId);

    List<Post> getAllFollowingPost(Long userId);
}
