package com.demo.text_based_social_media.api.post.adapter.in;

import com.demo.text_based_social_media.api.post.adapter.in.dto.PostCreateDto;
import com.demo.text_based_social_media.api.post.adapter.in.dto.PostViewDto;
import com.demo.text_based_social_media.api.post.adapter.in.dto.PostViewWithCommentsDto;
import com.demo.text_based_social_media.api.post.application.port.in.ReadPostUseCase;
import com.demo.text_based_social_media.api.post.application.port.in.SavePostUseCase;
import com.demo.text_based_social_media.config.security.services.UserDetailsImpl;
import com.demo.text_based_social_media.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class PostRestController {

    private final SavePostUseCase savePostUseCase;

    private final ReadPostUseCase readPostUseCase;

    private final PostMapper postMapper;

    @PostMapping(path = "/users/me/posts/new")
    public ResponseEntity<String> addPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostCreateDto postCreateDto) {
        Long userId = userDetails.getId();
        savePostUseCase.savePost(postMapper.toDomain(postCreateDto, userId));
        return ResponseEntity.status(HttpStatus.OK).body("Your post has been uploaded successfully.");
    }

    @GetMapping(path = "/users/me/posts")
    public List<PostViewWithCommentsDto> getAllPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        return readPostUseCase.getAllPostsByUserId(userId).stream()
                .map(postMapper::fromDomainWithComments)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/users/me/following/posts")
    public List<PostViewDto> getAllFollowingPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        return readPostUseCase.getAllFollowingPost(userId).stream()
                .map(postMapper::fromDomain)
                .collect(Collectors.toList());
    }
}
