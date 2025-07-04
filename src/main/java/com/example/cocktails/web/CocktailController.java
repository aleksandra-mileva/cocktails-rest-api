package com.example.cocktails.web;

import com.example.cocktails.model.dto.cocktail.CocktailViewModel;
import com.example.cocktails.model.dto.cocktail.SearchCocktailDto;
import com.example.cocktails.model.entity.enums.SpiritNameEnum;
import com.example.cocktails.service.CocktailService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/cocktails")
public class CocktailController {

  private final CocktailService cocktailService;

  public CocktailController(CocktailService cocktailService) {
    this.cocktailService = cocktailService;
  }

  @GetMapping("/all")
  public PagedModel<CocktailViewModel> allCocktails(Pageable pageable) {
    return cocktailService.findAllCocktailViewModels(pageable);
  }

  @GetMapping("/whiskey")
  public PagedModel<CocktailViewModel> whiskeyCocktails(Pageable pageable) {
    return cocktailService.findAllFilteredCocktailViewModels(SpiritNameEnum.WHISKEY, pageable);
  }

  @GetMapping("/search")
  public PagedModel<CocktailViewModel> searchQuery(@Valid SearchCocktailDto searchCocktailDto, Pageable pageable) {
    return cocktailService.searchCocktail(searchCocktailDto, pageable);
  }
}
