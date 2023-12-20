package com.demo.text_based_social_media.api.post.adapter.in;

import com.demo.text_based_social_media.api.post.adapter.in.dto.PostDto;
import com.demo.text_based_social_media.api.post.application.port.in.SavePostUseCase;
import com.demo.text_based_social_media.config.security.services.UserDetailsImpl;
import com.demo.text_based_social_media.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostRestController {

    private final SavePostUseCase savePostUseCase;

    private final PostMapper postMapper;

    @PostMapping(path = "/users/me/posts/new")
    public void addPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostDto postDto) {
        Long userId = userDetails.getId();
        savePostUseCase.savePost(postMapper.toDomain(postDto, userId));
    }

//    @Mapper
//    abstract static class PostRestControllerMapper {
//        private static final PostRestController.PostRestControllerMapper INSTANCE = Mappers.getMapper(PostRestController.PostRestControllerMapper.class);
//
//        @Mapping(target = "id", ignore = true)
//        @Mapping(target = "postedAt", ignore = true)
//        @Mapping(target = "creator", ignore = true)
//        @Mapping(target = "comments", ignore = true)
//        @Mapping(target = "creatorId", expression = "java(userId)")
//        abstract Post toDomain(PostDto postDto, Long userId);
//
//        abstract PostDto fromDomain(Post post);
//    }
}
