package com.demo.text_based_social_media.api.user.details.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {

    private String email;

    private String password;

    private String role;

    private LocalDateTime createdAt;

    private boolean premium;
}
