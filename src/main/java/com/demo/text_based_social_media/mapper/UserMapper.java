package com.demo.text_based_social_media.mapper;

import com.demo.text_based_social_media.api.role.domain.Role;
import com.demo.text_based_social_media.api.user.details.domain.User;
import com.demo.text_based_social_media.api.user.details.domain.UserCreateRequest;
import com.demo.text_based_social_media.api.user.details.domain.UserView;
import com.demo.text_based_social_media.entity.RoleEntity;
import com.demo.text_based_social_media.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {PostMapper.class, CommentMapper.class})
public interface UserMapper {

    UserView fromEntity(UserEntity userEntity);

    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "followers", ignore = true)
    @Mapping(target = "following", ignore = true)
    User toDomain(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "followers", ignore = true)
    @Mapping(target = "following", ignore = true)
    UserEntity fromDomain(UserCreateRequest userCreateRequest);

    @Mapping(target = "id", ignore = true)
    UserEntity fromDomain(User user);

    @Mapping(target = "id", ignore = true)
    RoleEntity fromDomain(Role role);
}
