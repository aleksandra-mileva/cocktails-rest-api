package com.example.cocktails.model.dto;

import com.example.cocktails.model.dto.cocktail.CocktailHomePageViewModel;

public record HomePageDto(
    CocktailHomePageViewModel cocktail,
    String message
) {
}
