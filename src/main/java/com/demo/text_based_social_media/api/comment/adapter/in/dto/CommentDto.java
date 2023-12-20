package com.demo.text_based_social_media.api.comment.adapter.in.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @Size(max = 1000)
    private String context = "";
}
