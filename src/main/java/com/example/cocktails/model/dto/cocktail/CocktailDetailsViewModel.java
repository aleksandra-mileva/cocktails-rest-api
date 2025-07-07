package com.example.cocktails.model.dto.cocktail;

import com.example.cocktails.model.dto.picture.PictureViewModel;
import com.example.cocktails.model.entity.CommentEntity;
import com.example.cocktails.model.entity.enums.FlavourEnum;
import com.example.cocktails.model.entity.enums.SpiritNameEnum;

import java.util.List;

public class CocktailDetailsViewModel {

  private Long id;
  private String name;
  private List<String> ingredients;
  private String preparation;
  private FlavourEnum flavour;
  private String author;
  private String videoId;
  private SpiritNameEnum spirit;
  private PictureViewModel picture;
  private List<CommentEntity> comments;
  private Integer percentAlcohol;
  private Integer servings;
  private boolean canDelete;
  boolean isFavorite;

  public boolean isFavorite() {
    return isFavorite;
  }

  public CocktailDetailsViewModel setIsFavorite(boolean favorite) {
    isFavorite = favorite;
    return this;
  }

  public boolean isCanDelete() {
    return canDelete;
  }

  public void setCanDelete(boolean canDelete) {
    this.canDelete = canDelete;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public SpiritNameEnum getSpirit() {
    return spirit;
  }

  public void setSpirit(SpiritNameEnum spirit) {
    this.spirit = spirit;
  }

  public String getName() {
    return name;
  }

  public CocktailDetailsViewModel setName(String name) {
    this.name = name;
    return this;
  }

  public List<String> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<String> ingredients) {
    this.ingredients = ingredients;
  }

  public String getPreparation() {
    return preparation;
  }

  public CocktailDetailsViewModel setPreparation(String preparation) {
    this.preparation = preparation;
    return this;
  }

  public FlavourEnum getFlavour() {
    return flavour;
  }

  public CocktailDetailsViewModel setFlavour(FlavourEnum flavour) {
    this.flavour = flavour;
    return this;
  }

  public String getAuthor() {
    return author;
  }

  public CocktailDetailsViewModel setAuthor(String author) {
    this.author = author;
    return this;
  }

  public String getVideoId() {
    return videoId;
  }

  public CocktailDetailsViewModel setVideoId(String videoId) {
    this.videoId = videoId;
    return this;
  }

  public PictureViewModel getPicture() {
    return picture;
  }

  public CocktailDetailsViewModel setPicture(PictureViewModel picture) {
    this.picture = picture;
    return this;
  }

  public List<CommentEntity> getComments() {
    return comments;
  }

  public CocktailDetailsViewModel setComments(List<CommentEntity> comments) {
    this.comments = comments;
    return this;
  }

  public Integer getPercentAlcohol() {
    return percentAlcohol;
  }

  public void setPercentAlcohol(Integer percentAlcohol) {
    this.percentAlcohol = percentAlcohol;
  }

  public Integer getServings() {
    return servings;
  }

  public void setServings(Integer servings) {
    this.servings = servings;
  }
}
