package com.example.cocktails.model.email;

import com.example.cocktails.model.entity.UserEntity;
import org.springframework.web.util.UriComponentsBuilder;

public class AccountVerificationEmailContext extends AbstractEmailContext {

  private static final String CRAFTY_COCKTAILS_EMAIL = "no-reply@craftyCocktails.com";
  private static final String VERIFICATION_SUBJECT = "registration_subject";
  private static final String TEMPLATE_LOCATION = "email/validation";

  private String baseUrl;
  private String token;

  public AccountVerificationEmailContext setToken(String token) {
    this.token = token;
    return this;
  }

  public AccountVerificationEmailContext setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
    return this;
  }

  @Override
  public <T> void initContext(T context) {
    UserEntity user = (UserEntity) context;

    setFrom(CRAFTY_COCKTAILS_EMAIL);
    setTo(user.getEmail());
    put("fullName", user.getFirstName() + " " + user.getLastName());
    put("verificationURL", buildVerificationUrl());
    setSubject(VERIFICATION_SUBJECT);
    setTemplateLocation(TEMPLATE_LOCATION);
  }

  private String buildVerificationUrl() {
    return UriComponentsBuilder.fromUriString(baseUrl)
        .path("/users/register/verify").queryParam("token", token).toUriString();
  }
}
