package com.demo.text_based_social_media.api.user.authentication.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDto {
    
    private final String token;
}
