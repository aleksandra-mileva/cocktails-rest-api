package com.example.cocktails.web;

import com.example.cocktails.model.dto.cocktail.AddCocktailDto;
import com.example.cocktails.model.dto.cocktail.CocktailDetailsViewModel;
import com.example.cocktails.model.dto.cocktail.CocktailViewModel;
import com.example.cocktails.model.dto.cocktail.SearchCocktailDto;
import com.example.cocktails.model.user.CustomUserDetails;
import com.example.cocktails.service.CocktailService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/cocktails")
public class CocktailController {

  private final CocktailService cocktailService;

  public CocktailController(CocktailService cocktailService) {
    this.cocktailService = cocktailService;
  }

  @GetMapping("/search")
  public PagedModel<CocktailViewModel> searchQuery(@Valid SearchCocktailDto searchCocktailDto, Pageable pageable) {
    return cocktailService.searchCocktail(searchCocktailDto, pageable);
  }

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public Long addCocktail(
      @Valid @RequestPart(name = "addCocktailDto") AddCocktailDto addCocktailDto,
      @RequestPart(name = "picture") MultipartFile picture,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    return cocktailService.addCocktail(addCocktailDto, picture, userDetails);
  }

  @GetMapping("/{id}")
  public CocktailDetailsViewModel details(
      @PathVariable Long id,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    return cocktailService.findCocktailDetailsViewModelById(id, userDetails);
  }

  @PreAuthorize("@userAuth.hasPermissionAuthorOfCocktailOrAdmin(#id)")
  @PutMapping("/{id}")
  public void updateCocktail(
      @PathVariable Long id,
      @Valid @RequestPart(name = "addCocktailDto") AddCocktailDto addCocktailDto,
      @RequestPart(name = "picture", required = false) MultipartFile picture,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    this.cocktailService.updateCocktail(id, addCocktailDto, picture, userDetails);
  }

  @PreAuthorize("@userAuth.hasPermissionAuthorOfCocktailOrAdmin(#id)")
  @DeleteMapping("/{id}")
  public void deleteCocktail(@PathVariable Long id) {
    cocktailService.deleteCocktailById(id);
  }
}
