package com.example.cocktails.service;

import com.example.cocktails.model.dto.HomePageDto;
import com.example.cocktails.model.dto.cocktail.CocktailHomePageViewModel;
import com.example.cocktails.model.entity.enums.TypeNameEnum;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class HomePageService {

  private final CocktailService cocktailService;

  public HomePageService(CocktailService cocktailService) {
    this.cocktailService = cocktailService;
  }

  public HomePageDto initHomePageDto() {
    CocktailHomePageViewModel cocktail;
    String message;

    LocalTime now = LocalTime.now();
    if (now.getHour() < 16) {
      cocktail = this.cocktailService.getRandomCocktailByType(TypeNameEnum.NON_ALCOHOLIC);
      message = "Need a non-alcoholic cocktail? This is our today's suggestion for you!";
    } else {
      cocktail = cocktailService.getRandomCocktailByType(TypeNameEnum.ALCOHOLIC);
      message = "It's party time! These are our today's alcoholic suggestions for you!";
    }

    return new HomePageDto(cocktail, message);
  }
}

