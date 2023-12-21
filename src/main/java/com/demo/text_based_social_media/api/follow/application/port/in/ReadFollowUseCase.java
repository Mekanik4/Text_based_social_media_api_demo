package com.demo.text_based_social_media.api.follow.application.port.in;

import com.demo.text_based_social_media.api.follow.domain.Follow;

import java.util.List;

public interface ReadFollowUseCase {

    Follow getFollowById(Long id);

    List<Follow> getAllByFollowerId(Long followerId);

    List<Follow> getAllByFollowingId(Long followingId);
}
