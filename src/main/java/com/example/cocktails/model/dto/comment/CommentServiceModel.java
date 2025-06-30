package com.example.cocktails.model.dto.comment;

public record CommentServiceModel(
    Long cocktailId,
    String message,
    String creator
) {
}
