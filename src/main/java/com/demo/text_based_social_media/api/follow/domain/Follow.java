package com.demo.text_based_social_media.api.follow.domain;

import com.demo.text_based_social_media.api.user.details.domain.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Follow {

    private Long id;

    private Long followerId;

    private Long followingId;

    private User follower;

    private User following;
}
