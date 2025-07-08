package com.example.cocktails.service;

import com.example.cocktails.model.dto.HomePageDto;
import com.example.cocktails.model.dto.cocktail.CocktailHomePageViewModel;
import com.example.cocktails.model.entity.enums.TypeNameEnum;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class HomePageService {

  private final CocktailService cocktailService;

  public HomePageService(CocktailService cocktailService) {
    this.cocktailService = cocktailService;
  }

  public HomePageDto initHomePageDto() {
    List<CocktailHomePageViewModel> cocktails;
    String message;

    LocalTime now = LocalTime.now();
    if (now.getHour() < 16) {
      cocktails = this.cocktailService.getThreeRandomCocktailsByType(TypeNameEnum.NON_ALCOHOLIC);
      message = "Need a non-alcoholic cocktail? This are our today's suggestions for you!";
    } else {
      cocktails = cocktailService.getThreeRandomCocktailsByType(TypeNameEnum.ALCOHOLIC);
      message = "It's party time! These are our today's alcoholic suggestions for you!";
    }

    return new HomePageDto(cocktails, message);
  }
}

