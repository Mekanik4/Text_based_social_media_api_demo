package com.demo.text_based_social_media.api.follow.application.port.out;

import com.demo.text_based_social_media.api.follow.domain.Follow;

public interface FollowSavePort {

    void save(Follow follow);

    void delete(Long followerId, Long followingId);
}
