package com.demo.text_based_social_media.api.user.authentication.adapter.in.dto;

import com.demo.text_based_social_media.entity.RoleType;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SignUpRequestDto {

    @NonNull
    private final String email;

    @NonNull
    @Size(min = 6, max = 40)
    private final String password;

    private final boolean premium;

    private final RoleType role;
    
}
