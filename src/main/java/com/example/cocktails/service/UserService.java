package com.example.cocktails.service;

import com.example.cocktails.model.dto.user.UserEditDto;
import com.example.cocktails.model.dto.user.UserViewModel;
import com.example.cocktails.model.entity.CocktailEntity;
import com.example.cocktails.model.entity.UserEntity;
import com.example.cocktails.model.user.CustomUserDetails;
import com.example.cocktails.repository.CocktailRepository;
import com.example.cocktails.repository.UserRepository;
import com.example.cocktails.web.exception.ObjectNotFoundException;
import com.example.cocktails.web.exception.UsernameChangedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final CocktailRepository cocktailRepository;

  public UserService(UserRepository userRepository, CocktailRepository cocktailRepository) {
    this.userRepository = userRepository;
    this.cocktailRepository = cocktailRepository;
  }

  public UserViewModel getUserInformation(Long id) {
    UserEntity userEntity = this.userRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("user", "User with ID " + id + " not found!"));
    List<String> roles = userEntity.getRoles().stream().map(r -> r.getRole().name()).toList();

    return new UserViewModel(
        userEntity.getId(),
        userEntity.getUsername(),
        userEntity.getFirstName(),
        userEntity.getLastName(),
        userEntity.getEmail(),
        roles
    );
  }

  @Transactional(noRollbackFor = UsernameChangedException.class)
  public void updateUserProfile(Long id, UserEditDto dto) {
    UserEntity existingUser = userRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("user", "User with ID " + id + " not found!"));

    validateProfileUpdate(existingUser, dto);

    boolean usernameChanged = !existingUser.getUsername().equalsIgnoreCase(dto.username());

    existingUser.setUsername(dto.username())
        .setEmail(dto.email())
        .setFirstName(dto.firstName())
        .setLastName(dto.lastName());

    userRepository.save(existingUser);

    if (usernameChanged) {
      throw new UsernameChangedException("Username changed. Please log in again.");
    }
  }

  @Transactional
  public boolean addOrRemoveCocktailFromFavorites(Long cocktailId, CustomUserDetails userDetails) {
    UserEntity user = this.userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new ObjectNotFoundException("user",
            "User with username " + userDetails.getUsername() + " was not found!"));

    CocktailEntity cocktail = cocktailRepository.findById(cocktailId)
        .orElseThrow(() -> new ObjectNotFoundException("cocktail", "Cocktail with id " + cocktailId + " not found!"));

    if (!user.getFavorites().contains(cocktail)) {
      user.getFavorites().add(cocktail);
    } else {
      user.getFavorites().remove(cocktail);
    }
    userRepository.save(user);
    return user.getFavorites().contains(cocktail);
  }

  public long getCountRegisteredUsers() {
    return this.userRepository.count();
  }

  private void validateProfileUpdate(UserEntity existingUser, UserEditDto dto) {
    String newUsername = dto.username();
    String newEmail = dto.email();

    if (!existingUser.getUsername().equalsIgnoreCase(newUsername)
        && userRepository.existsByUsername(newUsername)) {
      throw new IllegalArgumentException("This username " + newUsername + " is already taken.");
    }

    if (!existingUser.getEmail().equalsIgnoreCase(newEmail)
        && userRepository.existsByEmail(newEmail)) {
      throw new IllegalArgumentException("This email " + newEmail + " is already taken.");
    }
  }
}
