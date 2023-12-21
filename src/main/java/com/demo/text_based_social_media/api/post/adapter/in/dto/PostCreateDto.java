package com.demo.text_based_social_media.api.post.adapter.in.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateDto {

    @Size(max = 1000)
    private String context = "";
}
