package com.demo.text_based_social_media.api.comment.adapter.in;

import com.demo.text_based_social_media.api.comment.adapter.in.dto.CommentDto;
import com.demo.text_based_social_media.api.comment.application.port.in.ReadCommentUseCase;
import com.demo.text_based_social_media.api.comment.application.port.in.SaveCommentUseCase;
import com.demo.text_based_social_media.config.security.services.UserDetailsImpl;
import com.demo.text_based_social_media.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentRestController {

    private final SaveCommentUseCase saveCommentUseCase;

    private final ReadCommentUseCase readCommentUseCase;

    private final CommentMapper commentMapper;

    @PostMapping("/users/me/posts/{post_id}/comments/new")
    public void postComment(@PathVariable("post_id") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentDto commentDto) {
        Long userId = userDetails.getId();
        saveCommentUseCase.saveComment(commentMapper.toDomain(commentDto, userId, postId));
    }
}
