package com.demo.text_based_social_media.api.post.application.port.out;

import com.demo.text_based_social_media.api.post.domain.Post;

import java.util.List;

public interface PostReadPort {

    Post getPostById(Long id);

    List<Post> getAllPostsByUserId(Long userId);

    List<Post> getAllFollowingPosts(List<Long> userIds);

    List<Long> getPostIdsByUserId(Long userId);

    List<Long> getPostIdsByFollowingIds(List<Long> followingIds);

}
