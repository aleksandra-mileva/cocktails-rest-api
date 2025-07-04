package com.example.cocktails.web;

import com.example.cocktails.model.dto.cocktail.CocktailViewModel;
import com.example.cocktails.model.dto.response.AuthenticationResponse;
import com.example.cocktails.model.dto.user.UserEditDto;
import com.example.cocktails.model.dto.user.UserLoginDto;
import com.example.cocktails.model.dto.user.UserRegisterDto;
import com.example.cocktails.model.dto.user.UserView;
import com.example.cocktails.service.CocktailService;
import com.example.cocktails.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class UserRestController {

  private final UserService userService;
  private final CocktailService cocktailService;
  private final LocaleResolver localeResolver;

  public UserRestController(UserService userService, CocktailService cocktailService, LocaleResolver localeResolver) {
    this.userService = userService;
    this.cocktailService = cocktailService;
    this.localeResolver = localeResolver;
  }

  @PostMapping(path = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void register(
      @Valid @RequestBody UserRegisterDto userRegisterDto,
      HttpServletRequest request
  ) {
    this.userService.register(userRegisterDto, localeResolver.resolveLocale(request));
  }

  @GetMapping("/register/verify")
  public void verifyAccount(@RequestParam String token) {
    userService.verifyUser(token);
  }

  @PostMapping(path = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public AuthenticationResponse authenticateUser(@RequestBody UserLoginDto userLoginDto) {
    return userService.authenticate(userLoginDto);
  }

  @PreAuthorize("#id == authentication.principal.id")
  @GetMapping("/{id}")
  public UserView getUserInformation(@PathVariable Long id) {
    return userService.getUserInformation(id);
  }

  @PreAuthorize("#id == authentication.principal.id")
  @PutMapping("/{id}/editProfile")
  public void updateUserInformation(@PathVariable Long id, @Valid @RequestBody UserEditDto userEditDto) {
    userService.updateUserProfile(id, userEditDto);
  }

  @PreAuthorize("#id == authentication.principal.id")
  @GetMapping("/{id}/addedCocktails")
  public PagedModel<CocktailViewModel> addedCocktails(@PathVariable Long id, Pageable pageable) {
    return cocktailService.findAllCocktailsUploadedByUserId(id, pageable);
  }

  @PreAuthorize("#id == authentication.principal.id")
  @GetMapping("/{id}/favoriteCocktails")
  public PagedModel<CocktailViewModel> favoriteCocktails(@PathVariable Long id, Pageable pageable) {
    return cocktailService.findAllFavoriteCocktailsForUserId(id, pageable);
  }

// TODO: move to PictureController, use security to check if principal is owner of pic like
//  --> @userAuth.hasPermissionMatchingWarehouse(#inventoryRequestDTO.warehouseIdentifier)
//
//  @PreAuthorize("#id == authentication.principal.id")
//  @DeleteMapping("/{id}/deletePicture")
//  public String deletePicture(@PathVariable("id") Long id,
//      @RequestParam("pictureId") Long pictureId) {
//    Long idOfPic = pictureId;
//    pictureService.deletePicture(pictureId);
//    return "redirect:/users/profile/" + id + "/addedPictures";
//  }
}
