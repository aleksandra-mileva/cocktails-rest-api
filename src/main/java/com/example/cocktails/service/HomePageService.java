package com.example.cocktails.service;

import com.example.cocktails.model.dto.HomePageDto;
import com.example.cocktails.model.dto.picture.PictureHomePageViewModel;
import com.example.cocktails.model.entity.enums.TypeNameEnum;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class HomePageService {

  private final PictureService pictureService;

  public HomePageService(PictureService pictureService) {
    this.pictureService = pictureService;
  }

  public HomePageDto initHomePageDto() {
    List<PictureHomePageViewModel> pictures;
    String message = "";

    LocalTime now = LocalTime.now();
    if (now.getHour() < 16) {
      pictures = this.pictureService
          .getThreeRandomPicturesByCocktailType(TypeNameEnum.NON_ALCOHOLIC);
      message = "Need a non-alcoholic cocktail? This are our today's suggestions for you!";
    } else {
      pictures = this.pictureService
          .getThreeRandomPicturesByCocktailType(TypeNameEnum.ALCOHOLIC);
      message = "It's party time! These are our today's alcoholic suggestions for you!";
    }

    return new HomePageDto(pictures, message);
  }
}

