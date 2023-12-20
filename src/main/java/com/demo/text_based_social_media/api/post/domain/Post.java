package com.demo.text_based_social_media.api.post.domain;

import com.demo.text_based_social_media.api.comment.domain.Comment;
import com.demo.text_based_social_media.api.user.details.domain.User;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

    private Long id;

    @Size(max = 1000)
    private String context;

    private Long userId;

    private User user;

    private List<Comment> comments;

    private LocalDateTime postedAt;
}
