package com.example.cocktails.model.dto;

import com.example.cocktails.model.entity.enums.FlavourEnum;
import com.example.cocktails.model.entity.enums.SpiritNameEnum;
import com.example.cocktails.model.entity.enums.TypeNameEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record EditCocktailDto(
    Long id,
    @NotEmpty
    @Size(min = 5, max = 30)
    String name,
    @NotEmpty
    String ingredients,
    @NotEmpty
    String preparation,
    @NotNull
    FlavourEnum flavour,
    String videoUrl,
    TypeNameEnum type,
    @NotNull
    SpiritNameEnum spirit,
    @NotNull
    @Positive
    Integer percentAlcohol,
    @NotNull
    @Positive
    Integer servings
) {
}
