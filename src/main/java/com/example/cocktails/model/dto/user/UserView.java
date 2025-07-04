package com.example.cocktails.model.dto.user;

import com.example.cocktails.model.entity.RoleEntity;

import java.util.Set;

public record UserView(
    Long id,
    String username,
    String firstName,
    String lastName,
    String email,
    Set<RoleEntity> roles
) {
}
