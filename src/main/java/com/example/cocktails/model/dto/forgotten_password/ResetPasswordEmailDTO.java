package com.example.cocktails.model.dto.forgotten_password;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record ResetPasswordEmailDTO(
    @NotEmpty(message = "User email should be provided.")
    @Email(message = "User email should be valid.")
    String email
) {
}