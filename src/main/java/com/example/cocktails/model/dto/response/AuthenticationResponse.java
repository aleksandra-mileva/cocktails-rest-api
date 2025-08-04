package com.example.cocktails.model.dto.response;

import java.util.List;

public class AuthenticationResponse {
  private Long id;
  private String username;
  private String token;
  private List<String> authorities;

  public Long getId() {
    return id;
  }

  public AuthenticationResponse setId(Long id) {
    this.id = id;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public AuthenticationResponse setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getToken() {
    return token;
  }

  public AuthenticationResponse setToken(String token) {
    this.token = token;
    return this;
  }

  public List<String> getAuthorities() {
    return authorities;
  }

  public AuthenticationResponse setAuthorities(List<String> authorities) {
    this.authorities = authorities;
    return this;
  }
}
