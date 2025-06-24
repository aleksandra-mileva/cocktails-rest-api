package com.example.cocktails.model.dto;

import jakarta.validation.constraints.NotEmpty;

public record AddFortuneDto(
    @NotEmpty
    String content,
    @NotEmpty
    String author
) {
}
