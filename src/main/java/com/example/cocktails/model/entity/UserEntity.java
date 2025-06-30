package com.example.cocktails.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

  @NotNull
  private String username;

  @NotNull
  private String firstName;

  @NotNull
  private String lastName;

  @NotNull
  private String password;

  @NotNull
  private String email;

  @JoinTable(
      name = "users_roles",
      joinColumns = { @JoinColumn(name = "user_id") },
      inverseJoinColumns = { @JoinColumn(name = "role_id") }
  )
  @ManyToMany(fetch = FetchType.EAGER)
  private List<RoleEntity> roles;

  @OneToMany(mappedBy = "author")
  private List<CocktailEntity> addedCocktails;

  private boolean accountVerified;

  @CreationTimestamp
  private Timestamp createdOn;

  @ManyToMany()
  @JoinTable(
      name = "user_favorite_cocktails",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "cocktail_id"))
  private List<CocktailEntity> favorites;

  @OneToMany(mappedBy = "author")
  private List<PictureEntity> addedPictures;

  public void addRole(RoleEntity role) {
    if (this.roles == null) {
      this.roles = new ArrayList<>();
    }
    this.roles.add(role);
  }

  public String getUsername() {
    return username;
  }

  public UserEntity setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public UserEntity setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public UserEntity setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public UserEntity setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public UserEntity setEmail(String email) {
    this.email = email;
    return this;
  }

  public List<RoleEntity> getRoles() {
    return roles;
  }

  public UserEntity setRoles(List<RoleEntity> roles) {
    this.roles = roles;
    return this;
  }

  public List<CocktailEntity> getAddedCocktails() {
    return addedCocktails;
  }

  public UserEntity setAddedCocktails(List<CocktailEntity> addedCocktails) {
    this.addedCocktails = addedCocktails;
    return this;
  }

  public boolean isAccountVerified() {
    return accountVerified;
  }

  public UserEntity setAccountVerified(boolean accountVerified) {
    this.accountVerified = accountVerified;
    return this;
  }

  public Timestamp getCreatedOn() {
    return createdOn;
  }

  public UserEntity setCreatedOn(Timestamp createdOn) {
    this.createdOn = createdOn;
    return this;
  }

  public List<CocktailEntity> getFavorites() {
    return favorites;
  }

  public UserEntity setFavorites(List<CocktailEntity> favorites) {
    this.favorites = favorites;
    return this;
  }

  public List<PictureEntity> getAddedPictures() {
    return addedPictures;
  }

  public UserEntity setAddedPictures(List<PictureEntity> addedPictures) {
    this.addedPictures = addedPictures;
    return this;
  }
}
