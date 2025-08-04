package com.example.cocktails.config;

import com.example.cocktails.model.validation.ExceptionResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException {
    ExceptionResponseDTO body = new ExceptionResponseDTO();
    body.setDateTime(LocalDateTime.now());

    if (request.getRequestURI().equals("/api/auth/login") && request.getMethod().equals("POST")) {
      body.addError("user/password", "User or password is incorrect.");
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
    } else {
      body.addError("user/password", "Access denied.");
      response.setStatus(HttpStatus.FORBIDDEN.value());
    }

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(objectMapper.writeValueAsString(body));
  }
}
