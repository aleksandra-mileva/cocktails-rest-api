package com.example.cocktails.web.interceptor;

import com.example.cocktails.model.validation.ExceptionResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
public class MaintenanceInterceptor implements HandlerInterceptor {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
    if (isMaintenanceTime(LocalTime.now())) {
      ExceptionResponseDTO error = new ExceptionResponseDTO();
      error.setDateTime(LocalDateTime.now());
      error.getMessages().add("Service is under maintenance. Please try again later.");

      response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.getWriter().write(objectMapper.writeValueAsString(error));
      return false;
    }

    return true;
  }

  private boolean isMaintenanceTime(LocalTime now) {
    return now.isAfter(LocalTime.of(23, 59)) && now.isBefore(LocalTime.MAX);
  }
}

