package com.example.cocktails.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalTime;

@Configuration
public class MaintenanceInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    var requestURI = request.getRequestURI();

    LocalTime now = LocalTime.now();

    // Check if the current time is between 23:00 and 00:00
    if (!requestURI.equals("/api/maintenance") && isMaintenanceTime(now)) {
      response.sendRedirect("/api/maintenance");
      return false;
    }

    return HandlerInterceptor.super.preHandle(request, response, handler);
  }

  private boolean isMaintenanceTime(LocalTime now) {
    return now.isAfter(LocalTime.of(23, 59)) && now.isBefore(LocalTime.of(23, 59, 59, 999999999));
  }
}
