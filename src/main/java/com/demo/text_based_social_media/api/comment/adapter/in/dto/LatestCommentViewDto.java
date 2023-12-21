package com.demo.text_based_social_media.api.comment.adapter.in.dto;

import com.demo.text_based_social_media.api.post.adapter.in.dto.PostViewDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LatestCommentViewDto {

    private String context = "";

    private LocalDateTime postedAt;

    private PostViewDto post;
}
