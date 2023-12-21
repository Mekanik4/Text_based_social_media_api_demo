package com.demo.text_based_social_media.api.follow.application.port.out;

import com.demo.text_based_social_media.api.follow.domain.Follow;

import java.util.List;

public interface FollowReadPort {

    Follow getFollowById(Long id);

    Follow getFollowByFollowerIdAndFollowingId(Long followerId, Long followingId);

    List<Follow> getAllByFollowerId(Long followerId);

    List<Follow> getAllByFollowingId(Long followingId);

    List<Long> getFollowingIdsByFollowerId(Long followerId);
}
