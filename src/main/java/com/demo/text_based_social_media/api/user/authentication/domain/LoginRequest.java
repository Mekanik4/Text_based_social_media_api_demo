package com.demo.text_based_social_media.api.user.authentication.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    private String email;
    
    private String password;

}
