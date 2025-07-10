package com.example.cocktails.util;

import com.example.cocktails.model.dto.cocktail.CocktailEnumOption;

import java.util.Arrays;
import java.util.List;

public class EnumUtils {

  public static <E extends Enum<E>> List<CocktailEnumOption> toEnumOptions(Class<E> enumClass) {
    return Arrays.stream(enumClass.getEnumConstants())
        .map(e -> new CocktailEnumOption(formatName(e.name()), e.name()))
        .toList();
  }

  private static String formatName(String name) {
    String withSpaces = name.replace('_', ' ').toLowerCase();
    return capitalizeFirstLetter(withSpaces);
  }

  private static String capitalizeFirstLetter(String input) {
    return input.substring(0, 1).toUpperCase() + input.substring(1);
  }
}

