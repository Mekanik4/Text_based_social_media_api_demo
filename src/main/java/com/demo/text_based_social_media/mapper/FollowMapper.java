package com.demo.text_based_social_media.mapper;

import com.demo.text_based_social_media.api.follow.adapter.in.dto.FollowDto;
import com.demo.text_based_social_media.api.follow.adapter.in.dto.FollowViewDto;
import com.demo.text_based_social_media.api.follow.domain.Follow;
import com.demo.text_based_social_media.entity.FollowEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = UserMapper.class)
public interface FollowMapper {

    @Mapping(target = "followerId", expression = "java(followerId)")
    @Mapping(target = "followingId", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "follower", ignore = true)
    @Mapping(target = "following", ignore = true)
    Follow toDomain(FollowDto followDto, Long followerId);
    @Mapping(target = "follower", ignore = true)
    @Mapping(target = "following", ignore = true)
    Follow toDomain(FollowEntity followEntity);

    FollowEntity fromDomain(Follow follow);

    @Mapping(target = "email", source = "following.email")
    FollowViewDto followingDtoFromDomain(Follow follow);

    @Mapping(target = "email", source = "follower.email")
    FollowViewDto followerDtoFromDomain(Follow follow);
}
