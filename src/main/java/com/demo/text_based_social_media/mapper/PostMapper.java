package com.demo.text_based_social_media.mapper;

import com.demo.text_based_social_media.api.post.adapter.in.dto.PostCreateDto;
import com.demo.text_based_social_media.api.post.adapter.in.dto.PostViewDto;
import com.demo.text_based_social_media.api.post.adapter.in.dto.PostViewWithCommentsDto;
import com.demo.text_based_social_media.api.post.domain.Post;
import com.demo.text_based_social_media.entity.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {RoleMapper.class, UserMapper.class, CommentMapper.class})
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "postedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "userId", expression = "java(userId)")
    Post toDomain(PostCreateDto postCreateDto, Long userId);

    PostCreateDto dtoFromDomain(Post post);

    @Mapping(target = "user", ignore = true)
    Post toDomain(PostEntity postEntity);

    @Mapping(target = "id", ignore = true)
    PostEntity entityFromDomain(Post post);

    @Mapping(target = "email", source = "user.email")
    PostViewDto fromDomain(Post post);

    @Mapping(target = "email", source = "user.email")
    PostViewWithCommentsDto fromDomainWithComments(Post post);
}
