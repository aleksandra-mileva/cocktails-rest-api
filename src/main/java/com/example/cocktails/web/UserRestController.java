package com.example.cocktails.web;

import com.example.cocktails.model.dto.response.AuthenticationResponse;
import com.example.cocktails.model.dto.user.UserLoginDto;
import com.example.cocktails.model.dto.user.UserRegisterDto;
import com.example.cocktails.service.ResponseService;
import com.example.cocktails.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class UserRestController {

  private final UserService userService;
  private final LocaleResolver localeResolver;
  private final MessageSource messageSource;

  public UserRestController(UserService userService, LocaleResolver localeResolver, MessageSource messageSource) {
    this.userService = userService;
    this.localeResolver = localeResolver;
    this.messageSource = messageSource;
  }

  @PostMapping(path = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Object> register(
      @Valid @RequestBody UserRegisterDto userRegisterDto,
      HttpServletRequest request
  ) {

    this.userService.register(userRegisterDto, localeResolver.resolveLocale(request));

    Map<String, Object> body = ResponseService.generateGeneralResponse(
        String.format("User %s successfully registered", userRegisterDto.getEmail()));
    return new ResponseEntity<>(body, HttpStatus.OK);
  }

  @GetMapping("/register/verify")
  public ResponseEntity<Object> verifyAccount(@RequestParam(required = false) String token) {
    if (token == null) {
      Map<String, Object> body = ResponseService.generateGeneralResponse(messageSource
          .getMessage("user.registration.verification.missing.token",
              null,
              LocaleContextHolder.getLocale()));

      return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    userService.verifyUser(token);

    Map<String, Object> body = ResponseService.generateGeneralResponse(messageSource
        .getMessage("user.registration.verification.success",
            null,
            LocaleContextHolder.getLocale()));

    return new ResponseEntity<>(body, HttpStatus.OK);
  }

  @PostMapping(path = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody UserLoginDto userLoginDto) {

    return ResponseEntity.ok(userService.authenticate(userLoginDto));
  }
}
