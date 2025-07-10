package com.example.cocktails.web;

import com.example.cocktails.model.dto.cocktail.CocktailViewModel;
import com.example.cocktails.model.dto.user.UserEditDto;
import com.example.cocktails.model.dto.user.UserViewModel;
import com.example.cocktails.model.user.CustomUserDetails;
import com.example.cocktails.service.CocktailService;
import com.example.cocktails.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserRestController {

  private final UserService userService;
  private final CocktailService cocktailService;

  public UserRestController(UserService userService, CocktailService cocktailService) {
    this.userService = userService;
    this.cocktailService = cocktailService;
  }

  @PreAuthorize("#id == authentication.principal.id")
  @GetMapping("/{id}")
  public UserViewModel getUserInformation(@PathVariable Long id) {
    return userService.getUserInformation(id);
  }

  @PreAuthorize("#id == authentication.principal.id")
  @PutMapping("/{id}")
  public void updateUserInformation(@PathVariable Long id, @Valid @RequestBody UserEditDto userEditDto) {
    userService.updateUserProfile(id, userEditDto);
  }

  @PreAuthorize("#id == authentication.principal.id")
  @GetMapping("/{id}/cocktails")
  public PagedModel<CocktailViewModel> addedCocktails(@PathVariable Long id, Pageable pageable) {
    return cocktailService.findAllCocktailsUploadedByUserId(id, pageable);
  }

  @PreAuthorize("#id == authentication.principal.id")
  @GetMapping("/{id}/favorites")
  public PagedModel<CocktailViewModel> favoriteCocktails(@PathVariable Long id, Pageable pageable) {
    return cocktailService.findAllFavoriteCocktailsForUserId(id, pageable);
  }

  @PostMapping("/favourites/{cocktailId}")
  @ResponseBody
  public boolean addOrRemoveFromFavorites(
      @PathVariable Long cocktailId,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    return userService.addOrRemoveCocktailFromFavorites(cocktailId, userDetails);
  }
}
