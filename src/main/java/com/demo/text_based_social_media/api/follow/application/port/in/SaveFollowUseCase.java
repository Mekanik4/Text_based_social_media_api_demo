package com.demo.text_based_social_media.api.follow.application.port.in;

import com.demo.text_based_social_media.api.follow.adapter.in.dto.FollowDto;

public interface SaveFollowUseCase {

    void saveFollow(FollowDto followDto, Long userId);

    void deleteFollow(FollowDto followDto, Long userId);
}
