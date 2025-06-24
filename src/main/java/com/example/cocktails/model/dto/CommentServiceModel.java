package com.example.cocktails.model.dto;

public record CommentServiceModel(
    Long cocktailId,
    String message,
    String creator
) {
}
