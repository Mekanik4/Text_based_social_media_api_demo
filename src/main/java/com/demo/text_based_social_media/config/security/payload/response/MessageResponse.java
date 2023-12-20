package com.demo.text_based_social_media.config.security.payload.response;

import lombok.Getter;
import lombok.Setter;

public class MessageResponse {
    @Getter
    @Setter
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

}
