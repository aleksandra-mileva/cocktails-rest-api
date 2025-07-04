package com.example.cocktails.service;

import com.example.cocktails.model.entity.RoleEntity;
import com.example.cocktails.model.entity.UserEntity;
import com.example.cocktails.model.user.CustomUserDetails;
import com.example.cocktails.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private static final Logger logger = LoggerFactory.getLogger(CustomUserDetails.class);

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.info("Trying to load user by username: {}", username);

    return this.userRepository
        .findByUsername(username)
        .map(this::map)
        .orElseThrow(() -> {
          logger.error("User with username {} not found.", username);
          return new UsernameNotFoundException("User with username " + username + " not found.");
        });
  }

  private UserDetails map(UserEntity userEntity) {
    logger.info("Mapping user details for user with email: {}", userEntity.getEmail());

    return new CustomUserDetails(
        userEntity.getId(),
        userEntity.getUsername(),
        userEntity.getFirstName() + " " + userEntity.getLastName(),
        userEntity.getPassword(),
        userEntity
            .getRoles()
            .stream()
            .map(this::map)
            .toList(),
        userEntity.isAccountVerified());
  }

  private GrantedAuthority map(RoleEntity userRole) {
    logger.info("Mapping user role: {}", userRole.getRole());

    return new SimpleGrantedAuthority("ROLE_" + userRole.getRole().name());
  }
}
