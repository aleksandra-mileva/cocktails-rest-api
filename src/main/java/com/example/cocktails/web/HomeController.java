package com.example.cocktails.web;


import com.example.cocktails.model.dto.HomePageDto;
import com.example.cocktails.service.HomePageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(
    origins = "http://localhost:4200",
    allowCredentials = "true"
)
@RestController
@RequestMapping("/api")
public class HomeController {

  private final HomePageService homePageService;

  public HomeController(HomePageService homePageService) {
    this.homePageService = homePageService;
  }

  @GetMapping("/")
  public HomePageDto index() {
    return this.homePageService.initHomePageDto();
  }
}
