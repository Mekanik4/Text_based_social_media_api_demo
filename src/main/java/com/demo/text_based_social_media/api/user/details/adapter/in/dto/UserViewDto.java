package com.demo.text_based_social_media.api.user.details.adapter.in.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class UserViewDto {
    private final String email;

    private final boolean premium;

    private final LocalDateTime createdAt;
}
