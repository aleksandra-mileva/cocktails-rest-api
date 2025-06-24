package com.example.cocktails.model.dto;

import com.example.cocktails.model.entity.enums.FlavourEnum;
import com.example.cocktails.model.entity.enums.SpiritNameEnum;
import com.example.cocktails.model.entity.enums.TypeNameEnum;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record SearchCocktailDto(
    @Size(max = 30)
    String name,
    FlavourEnum flavour,
    TypeNameEnum type,
    SpiritNameEnum spirit,
    @Positive
    Integer minPercentAlcohol,
    @Positive
    Integer maxPercentAlcohol
) {
}
