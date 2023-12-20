package com.demo.text_based_social_media.api.user.details.domain;

import com.demo.text_based_social_media.api.comment.domain.Comment;
import com.demo.text_based_social_media.api.post.domain.Post;
import com.demo.text_based_social_media.api.role.domain.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    
    private Long id;
    
    private String email;

    private String password;
    
    private Role role;

    private LocalDateTime createdAt;

    private boolean premium;

    private List<Post> posts;

    private List<Comment> comments;

    private List<User> followers;

    private List<User> following;
}
