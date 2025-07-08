package com.example.cocktails.model.dto;

import com.example.cocktails.model.dto.cocktail.CocktailHomePageViewModel;

import java.util.List;

public record HomePageDto(
    List<CocktailHomePageViewModel> cocktails,
    String message
) {
}
