package com.demo.text_based_social_media.api.user.details.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserView {
    private String email;

    private boolean premium;

    private LocalDateTime createdAt;
}
