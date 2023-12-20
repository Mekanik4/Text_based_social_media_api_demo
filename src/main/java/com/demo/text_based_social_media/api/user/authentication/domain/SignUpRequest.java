package com.demo.text_based_social_media.api.user.authentication.domain;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest {

    private String email;

    private String password;

    private String role;

    private boolean premium;
}
