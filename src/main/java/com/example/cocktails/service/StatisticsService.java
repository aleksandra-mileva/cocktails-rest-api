package com.example.cocktails.service;

import com.example.cocktails.model.dto.statistics.StatisticsViewModel;
import com.example.cocktails.model.entity.enums.SpiritNameEnum;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StatisticsService {

  private final CocktailService cocktailService;
  private final UserService userService;

  public StatisticsService(CocktailService cocktailService, UserService userService) {
    this.cocktailService = cocktailService;
    this.userService = userService;
  }

  public StatisticsViewModel getStatistics() {
    return new StatisticsViewModel(
        this.cocktailService.findCountAll(),
        this.cocktailService.findCountBySpirit(SpiritNameEnum.WHISKEY),
        this.cocktailService.findCountBySpirit(SpiritNameEnum.TEQUILA),
        this.cocktailService.findCountBySpirit(SpiritNameEnum.GIN),
        this.cocktailService.findCountBySpirit(SpiritNameEnum.RUM),
        this.cocktailService.findCountBySpirit(SpiritNameEnum.VODKA),
        this.cocktailService.findCountBySpirit(SpiritNameEnum.BRANDY),
        this.cocktailService.findCountBySpirit(SpiritNameEnum.NONE),
        this.userService.getCountRegisteredUsers(),
        LocalDateTime.now()
    );
  }


}
