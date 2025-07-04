package com.example.cocktails.model.dto.statistics;

import java.time.LocalDateTime;

public record StatisticsViewDto(
    long allCocktails,
    long whiskeyCocktails,
    long tequilaCocktails,
    long ginCocktails,
    long rumCocktails,
    long vodkaCocktails,
    long brandyCocktails,
    long nonAlcoholicCocktails,
    long usersCount,
    LocalDateTime localDateTime
) {
}
