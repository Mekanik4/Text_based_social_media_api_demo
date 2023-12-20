package com.demo.text_based_social_media.api.comment.domain;

import com.demo.text_based_social_media.api.post.domain.Post;
import com.demo.text_based_social_media.api.user.details.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    private Long id;

    private String context;

    private Long userId;

    private Long postId;

    private User user;

    private Post post;

    private LocalDateTime postedAt;
}
