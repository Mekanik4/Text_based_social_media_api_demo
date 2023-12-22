package com.demo.text_based_social_media.api.follow.adapter.out;

import com.demo.text_based_social_media.api.follow.application.port.out.FollowReadPort;
import com.demo.text_based_social_media.api.follow.application.port.out.FollowSavePort;
import com.demo.text_based_social_media.api.follow.domain.Follow;
import com.demo.text_based_social_media.mapper.FollowMapper;
import com.demo.text_based_social_media.repository.FollowRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
@Transactional
public class FollowAdapter implements FollowReadPort, FollowSavePort {

    private final FollowRepository followRepository;

    private final FollowMapper followMapper;
    @Override
    public Follow getFollowById(Long id) {
        return followMapper.toDomain(followRepository.getById(id));
    }

    @Override
    public Follow getFollowByFollowerIdAndFollowingId(Long followerId, Long followingId) {
        return followMapper.toDomain(followRepository.getByFollowerIdAndFollowingId(followerId, followingId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Follower with id " + followingId + " not found")
        ));
    }

    @Override
    public List<Follow> getAllByFollowerId(Long followerId) {
        return followRepository.getAllByFollowerId(followerId).stream()
                .map(followMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Follow> getAllByFollowingId(Long followingId) {
        return followRepository.getAllByFollowingId(followingId).stream()
                .map(followMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getFollowingIdsByFollowerId(Long followerId) {
        return followRepository.getFollowingIdsByFollowerId(followerId);
    }

    @Override
    public void save(Follow follow) {
        if (followRepository.existsByFollowerIdAndFollowingId(follow.getFollowerId(), follow.getFollowingId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already follow " + follow.getFollowing().getEmail());
        followRepository.save(followMapper.fromDomain(follow));
    }

    @Override
    public void delete(Long followerId, Long followingId) {
        if (followRepository.existsByFollowerIdAndFollowingId(followerId, followingId))
            followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You don't follow user with id " + followingId, new RuntimeException());
    }
}
