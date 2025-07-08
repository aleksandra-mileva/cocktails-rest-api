package com.example.cocktails.service;

import com.example.cocktails.model.user.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("userAuth")
public class UserAuthorizationService {

  private final CocktailService cocktailService;

  public UserAuthorizationService(CocktailService cocktailService) {
    this.cocktailService = cocktailService;
  }

  public boolean hasPermissionAuthorOfCocktailOrAdmin(Long cocktailId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
      return false;
    }

    return cocktailService.isOwner(userDetails.getUsername(), cocktailId);
  }
}
