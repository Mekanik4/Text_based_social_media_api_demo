package com.demo.text_based_social_media.mapper;

import com.demo.text_based_social_media.api.comment.adapter.in.dto.CommentDto;
import com.demo.text_based_social_media.api.comment.domain.Comment;
import com.demo.text_based_social_media.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {UserMapper.class, PostMapper.class, RoleMapper.class})
public interface CommentMapper {

    @Mapping(target = "postId", source = "postId")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "post", ignore = true)
    Comment toDomain(CommentEntity commentEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "postedAt", ignore = true)
    @Mapping(target = "userId", expression = "java(userId)")
    @Mapping(target = "postId", expression = "java(postId)")
    Comment toDomain(CommentDto commentDto, Long userId, Long postId);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "postId", source = "postId")
    CommentEntity fromDomain(Comment comment);
}
