package com.example.cocktails.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final AuthenticationEntryPoint authenticationEntryPoint;

  private final String[] whiteList = new String[]{
      "/",
      "/api/cocktails/**",
      "/api/**",
      "/api/maintenance/**",
      "/api/auth/register/verify/**"
  };

  private final String[] anonymousList = new String[]{
      "/api/auth/login",
      "/api/auth/register",
      "/api/password/**"
  };

  // whiteList: "/", "/cocktails/**", "/api/**", "/maintenance/**"
  // anonymousList: "/users/register/**", "/users/login", "/password/**"
  // authenticated: "/users/profile", "/cocktails/add", "/users/profile/**", "/fortunes/fortune"
  // for Admin only: "/statistics", "/fortunes", "/fortunes/**"

  public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider,
      AuthenticationEntryPoint authenticationEntryPoint) {
    this.jwtAuthFilter = jwtAuthFilter;
    this.authenticationProvider = authenticationProvider;
    this.authenticationEntryPoint = authenticationEntryPoint;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.GET, whiteList).permitAll()
            .requestMatchers(HttpMethod.POST, anonymousList).anonymous()
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .exceptionHandling(ex -> ex
            .authenticationEntryPoint(authenticationEntryPoint)
        )
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .build();
  }
}
