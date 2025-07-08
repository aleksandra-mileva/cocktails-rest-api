package com.example.cocktails.web;

import com.example.cocktails.model.dto.response.AuthenticationResponse;
import com.example.cocktails.model.dto.user.UserLoginDto;
import com.example.cocktails.model.dto.user.UserRegisterDto;
import com.example.cocktails.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;
  private final LocaleResolver localeResolver;

  public AuthController(AuthService authService, LocaleResolver localeResolver) {
    this.authService = authService;
    this.localeResolver = localeResolver;
  }

  @PostMapping(path = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void register(
      @Valid @RequestBody UserRegisterDto userRegisterDto,
      HttpServletRequest request
  ) {
    authService.register(userRegisterDto, localeResolver.resolveLocale(request));
  }

  @GetMapping("/register/verify")
  public void verifyAccount(@RequestParam String token) {
    authService.verifyUser(token);
  }

  @PostMapping(path = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public AuthenticationResponse authenticateUser(@RequestBody UserLoginDto userLoginDto) {
    return authService.authenticate(userLoginDto);
  }
}
