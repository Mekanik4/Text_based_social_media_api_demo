package com.demo.text_based_social_media.api.post.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostViewDto {

    private String email = "";

    private String context = "";

    private LocalDateTime postedAt;

}
