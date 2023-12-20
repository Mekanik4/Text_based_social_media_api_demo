package com.demo.text_based_social_media.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoleType {
    
    ADMIN("ADMIN"),
    USER("USER");

    @Getter
    private final String name;
}
