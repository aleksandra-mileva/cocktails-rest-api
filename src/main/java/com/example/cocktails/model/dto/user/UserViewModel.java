package com.example.cocktails.model.dto.user;

import java.util.List;

public record UserViewModel(
    Long id,
    String username,
    String firstName,
    String lastName,
    String email,
    List<String> roles
) {
}
