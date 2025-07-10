package com.example.cocktails.model.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddCommentDto(
    @NotBlank
    @Size(min = 10)
    String text
) {
}
