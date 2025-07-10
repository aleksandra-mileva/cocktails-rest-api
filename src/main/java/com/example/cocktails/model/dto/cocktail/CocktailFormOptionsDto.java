package com.example.cocktails.model.dto.cocktail;

import java.util.List;

public record CocktailFormOptionsDto(
    List<CocktailEnumOption> flavours,
    List<CocktailEnumOption> spirits,
    List<CocktailEnumOption> types
) {
}
