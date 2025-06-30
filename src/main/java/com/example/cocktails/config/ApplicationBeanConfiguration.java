package com.example.cocktails.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApplicationBeanConfiguration {

  private final CloudConfig cloudConfig;
  private final UserDetailsService userDetailsService;

  @Value("${fortuneservice.base.url}")
  private String addressBaseUrl;

  public ApplicationBeanConfiguration(CloudConfig cloudConfig, UserDetailsService userDetailsService) {
    this.cloudConfig = cloudConfig;
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public WebClient webClient() {
    return WebClient.builder().baseUrl(addressBaseUrl).build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return new CustomAuthenticationEntryPoint();
  }

  @Bean
  public Cloudinary createCloudinaryConfig() {
    Map<String, Object> config = new HashMap<>();
    config.put("cloud_name", cloudConfig.getCloudName());
    config.put("api_key", cloudConfig.getApiKey());
    config.put("api_secret", cloudConfig.getApiSecret());
    return new Cloudinary(config);
  }
}
