package com.example.cocktails.web;

import com.example.cocktails.model.dto.statistics.StatisticsViewDto;
import com.example.cocktails.service.StatisticsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

  private final StatisticsService statisticsService;

  public StatisticsController(StatisticsService statisticsService) {
    this.statisticsService = statisticsService;
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @GetMapping
  public StatisticsViewDto statistics() {
    return statisticsService.getStatistics();
  }
}
