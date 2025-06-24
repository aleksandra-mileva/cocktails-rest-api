package com.example.cocktails.model.entity;

import com.example.cocktails.model.entity.enums.FlavourEnum;
import com.example.cocktails.model.entity.enums.SpiritNameEnum;
import com.example.cocktails.model.entity.enums.TypeNameEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "cocktails")
public class CocktailEntity extends BaseEntity {

  @NotBlank
  private String name;

  @NotBlank
  private String ingredients;

  @Column(columnDefinition = "LONGTEXT")
  @NotBlank
  private String preparation;

  @NotNull
  private Integer percentAlcohol;

  @NotNull
  private Integer servings;

  private String videoUrl;

  @Enumerated(EnumType.STRING)
  @NotNull
  private FlavourEnum flavour;

  @Enumerated(EnumType.STRING)
  @NotNull
  private TypeNameEnum type;

  @Enumerated(EnumType.STRING)
  @NotNull
  private SpiritNameEnum spirit;

  @ManyToOne
  @NotNull
  private UserEntity author;

  @OneToMany(mappedBy = "cocktail")
  private List<PictureEntity> pictures;

  @OneToMany(mappedBy = "cocktail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<CommentEntity> comments;

  @ManyToMany(mappedBy = "favorites")
  private List<UserEntity> favoriteUsers;

  public String getName() {
    return name;
  }

  public CocktailEntity setName(String name) {
    this.name = name;
    return this;
  }

  public String getIngredients() {
    return ingredients;
  }

  public CocktailEntity setIngredients(String ingredients) {
    this.ingredients = ingredients;
    return this;
  }

  public String getPreparation() {
    return preparation;
  }

  public CocktailEntity setPreparation(String preparation) {
    this.preparation = preparation;
    return this;
  }

  public Integer getPercentAlcohol() {
    return percentAlcohol;
  }

  public CocktailEntity setPercentAlcohol(Integer percentAlcohol) {
    this.percentAlcohol = percentAlcohol;
    return this;
  }

  public Integer getServings() {
    return servings;
  }

  public CocktailEntity setServings(Integer servings) {
    this.servings = servings;
    return this;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public CocktailEntity setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
    return this;
  }

  public FlavourEnum getFlavour() {
    return flavour;
  }

  public CocktailEntity setFlavour(FlavourEnum flavour) {
    this.flavour = flavour;
    return this;
  }

  public TypeNameEnum getType() {
    return type;
  }

  public CocktailEntity setType(TypeNameEnum type) {
    this.type = type;
    return this;
  }

  public SpiritNameEnum getSpirit() {
    return spirit;
  }

  public CocktailEntity setSpirit(SpiritNameEnum spirit) {
    this.spirit = spirit;
    return this;
  }

  public UserEntity getAuthor() {
    return author;
  }

  public CocktailEntity setAuthor(UserEntity author) {
    this.author = author;
    return this;
  }

  public List<PictureEntity> getPictures() {
    return pictures;
  }

  public CocktailEntity setPictures(List<PictureEntity> pictures) {
    this.pictures = pictures;
    return this;
  }

  public List<CommentEntity> getComments() {
    return comments;
  }

  public CocktailEntity setComments(List<CommentEntity> comments) {
    this.comments = comments;
    return this;
  }

  public List<UserEntity> getFavoriteUsers() {
    return favoriteUsers;
  }

  public CocktailEntity setFavoriteUsers(List<UserEntity> favoriteUsers) {
    this.favoriteUsers = favoriteUsers;
    return this;
  }
}
