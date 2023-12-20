package com.demo.text_based_social_media.api.user.authentication.adapter.in.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginRequestDto {

    @NonNull
    private final String email;

    @NonNull
    private final String password;

}
