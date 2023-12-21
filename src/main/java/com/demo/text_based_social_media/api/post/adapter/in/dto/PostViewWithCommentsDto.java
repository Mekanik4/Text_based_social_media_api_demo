package com.demo.text_based_social_media.api.post.adapter.in.dto;

import com.demo.text_based_social_media.api.comment.adapter.in.dto.CommentViewDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostViewWithCommentsDto {

    private String email = "";

    private String context = "";

    private LocalDateTime postedAt;

    private List<CommentViewDto> comments;

}
