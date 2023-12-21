package com.demo.text_based_social_media.mapper;

import com.demo.text_based_social_media.api.comment.adapter.in.dto.CommentDto;
import com.demo.text_based_social_media.api.comment.adapter.in.dto.CommentViewDto;
import com.demo.text_based_social_media.api.comment.adapter.in.dto.LatestCommentViewDto;
import com.demo.text_based_social_media.api.comment.domain.Comment;
import com.demo.text_based_social_media.api.post.adapter.in.dto.PostViewDto;
import com.demo.text_based_social_media.api.post.domain.Post;
import com.demo.text_based_social_media.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

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

    @Mapping(target = "email", source = "user.email")
    CommentViewDto viewDtoFromDomain(Comment comment);

    @Mapping(source = "post", target = "post", qualifiedByName = "postViewDtoFromDomain")
    LatestCommentViewDto latestCommentsDtoFromDomain(Comment comment);

    @Named("postViewDtoFromDomain")
    @Mapping(target = "email", source = "user.email")
    PostViewDto postViewDtoFromDomain(Post post);
}
