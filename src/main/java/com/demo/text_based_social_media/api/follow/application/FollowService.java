package com.demo.text_based_social_media.api.follow.application;

import com.demo.text_based_social_media.api.follow.adapter.in.dto.FollowDto;
import com.demo.text_based_social_media.api.follow.application.port.in.ReadFollowUseCase;
import com.demo.text_based_social_media.api.follow.application.port.in.SaveFollowUseCase;
import com.demo.text_based_social_media.api.follow.application.port.out.FollowReadPort;
import com.demo.text_based_social_media.api.follow.application.port.out.FollowSavePort;
import com.demo.text_based_social_media.api.follow.domain.Follow;
import com.demo.text_based_social_media.api.user.authentication.application.port.out.ReadUserPort;
import com.demo.text_based_social_media.api.user.details.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class FollowService implements ReadFollowUseCase, SaveFollowUseCase {

    private final FollowReadPort followReadPort;

    private final FollowSavePort followSavePort;

    private final ReadUserPort readUserPort;
    @Override
    public Follow getFollowById(Long id) {
        return followReadPort.getFollowById(id);
    }

    @Override
    public List<Follow> getAllByFollowerId(Long followerId) {
        return followReadPort.getAllByFollowerId(followerId);
    }


    @Override
    public void saveFollow(FollowDto followDto, Long userId) {
        User followingUser = readUserPort.readUserByEmail(followDto.getFollowingName());
        Follow follow = new Follow();
        follow.setFollowerId(userId);
        follow.setFollowingId(followingUser.getId());
        follow.setFollowing(followingUser);
        followSavePort.save(follow);
    }

    @Override
    public void deleteFollow(FollowDto followDto, Long userId) {
        followSavePort.delete(userId, readUserPort.readUserByEmail(followDto.getFollowingName()).getId());
    }
}
