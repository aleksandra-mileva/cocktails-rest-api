package com.example.cocktails.config;

import com.example.cocktails.model.validation.ExceptionResponseDTO;
import com.example.cocktails.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      String jwtToken = parse(request);
      if (StringUtils.isNotBlank(jwtToken) && jwtService.validateToken(jwtToken)) {
        authenticateRequest(request, jwtToken);
      }
    } catch (JwtException e) {
      writeError(response, HttpServletResponse.SC_UNAUTHORIZED,
          "Invalid or expired token, you may login and try again.");
      return;
    } catch (Exception e) {
      writeError(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
      return;
    }

    filterChain.doFilter(request, response);
  }

  private void writeError(HttpServletResponse response, int status, String message) throws IOException {
    ExceptionResponseDTO error = new ExceptionResponseDTO();
    error.setDateTime(LocalDateTime.now());
    error.addError("token", message);

    response.setStatus(status);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(new ObjectMapper().writeValueAsString(error));
  }

  private void authenticateRequest(HttpServletRequest request, String jwtToken) {
    String username = jwtService.getUsernameFromToken(jwtToken);
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if (username.equals(userDetails.getUsername())) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
  }

  private String parse(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (StringUtils.isNotBlank(authHeader) && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }
    return null;
  }
}
