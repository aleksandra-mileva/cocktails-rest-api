package com.example.cocktails.model.dto.response;

public record AuthenticationResponse(
    Long id, String username, String token
) {
}
