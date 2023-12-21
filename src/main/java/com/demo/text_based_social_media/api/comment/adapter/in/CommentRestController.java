package com.demo.text_based_social_media.api.comment.adapter.in;

import com.demo.text_based_social_media.api.comment.adapter.in.dto.CommentDto;
import com.demo.text_based_social_media.api.comment.adapter.in.dto.LatestCommentViewDto;
import com.demo.text_based_social_media.api.comment.application.port.in.ReadCommentUseCase;
import com.demo.text_based_social_media.api.comment.application.port.in.SaveCommentUseCase;
import com.demo.text_based_social_media.config.security.services.UserDetailsImpl;
import com.demo.text_based_social_media.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class CommentRestController {

    private final SaveCommentUseCase saveCommentUseCase;

    private final ReadCommentUseCase readCommentUseCase;

    private final CommentMapper commentMapper;

    @PostMapping("/users/me/posts/{post_id}/comments/new")
    public ResponseEntity<String> postComment(@PathVariable("post_id") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentDto commentDto) {
        Long userId = userDetails.getId();
        saveCommentUseCase.saveComment(commentMapper.toDomain(commentDto, userId, postId));
        return ResponseEntity.status(HttpStatus.OK).body("Comment posted.");
    }

    @GetMapping("/users/me/comments/latest")
    public List<LatestCommentViewDto> getLatestComments(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userId = userDetails.getId();
        return readCommentUseCase.getLatestCommentsByUserId(userId).stream()
                .map(commentMapper::latestCommentsDtoFromDomain)
                .collect(Collectors.toList());
    }
}
