package com.example.cocktails.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserEditDto(
    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Must be valid email address.")
    String email,
    @NotEmpty(message = "Username cannot be empty.")
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!")
    String username,
    @NotEmpty(message = "First name cannot be empty.")
    @Size(min = 2, max = 20, message = "First name length must be between 2 and 20 characters!")
    String firstName,
    @NotEmpty(message = "Last name cannot be empty.")
    @Size(min = 2, max = 20, message = "Last name length must be between 2 and 20 characters!")
    String lastName
) {
}
