package com.example.cocktails.model.dto.cocktail;

import com.example.cocktails.model.dto.picture.PictureViewModel;
import com.example.cocktails.model.entity.enums.FlavourEnum;
import com.example.cocktails.model.entity.enums.SpiritNameEnum;
import com.example.cocktails.model.entity.enums.TypeNameEnum;

import java.util.List;

public class CocktailDetailsViewModel {

  private Long id;
  private String name;
  private String ingredientsRaw;
  private String preparation;
  private FlavourEnum flavour;
  private String authorFirstName;
  private String authorLastName;
  private String videoUrl;
  private SpiritNameEnum spirit;
  private String pictureUrl;
  private Integer percentAlcohol;
  private Integer servings;
  private TypeNameEnum type;

  // Not from DB – set manually
  private List<String> ingredients;
  private String author;
  private String videoId;
  private PictureViewModel picture;
  private boolean canDelete;
  private boolean isFavorite;

  public CocktailDetailsViewModel(
      Long id,
      String name,
      String ingredientsRaw,
      String preparation,
      FlavourEnum flavour,
      String authorFirstName,
      String authorLastName,
      String videoUrl,
      SpiritNameEnum spirit,
      String pictureUrl,
      Integer percentAlcohol,
      Integer servings,
      TypeNameEnum type,
      PictureViewModel picture
  ) {
    this.id = id;
    this.name = name;
    this.ingredientsRaw = ingredientsRaw;
    this.preparation = preparation;
    this.flavour = flavour;
    this.authorFirstName = authorFirstName;
    this.authorLastName = authorLastName;
    this.videoUrl = videoUrl;
    this.spirit = spirit;
    this.pictureUrl = pictureUrl;
    this.percentAlcohol = percentAlcohol;
    this.servings = servings;
    this.type = type;
    this.picture = picture;
  }

  public Long getId() {
    return id;
  }

  public CocktailDetailsViewModel setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public CocktailDetailsViewModel setName(String name) {
    this.name = name;
    return this;
  }

  public String getIngredientsRaw() {
    return ingredientsRaw;
  }

  public CocktailDetailsViewModel setIngredientsRaw(String ingredientsRaw) {
    this.ingredientsRaw = ingredientsRaw;
    return this;
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

  public String getAuthorFirstName() {
    return authorFirstName;
  }

  public CocktailDetailsViewModel setAuthorFirstName(String authorFirstName) {
    this.authorFirstName = authorFirstName;
    return this;
  }

  public String getAuthorLastName() {
    return authorLastName;
  }

  public CocktailDetailsViewModel setAuthorLastName(String authorLastName) {
    this.authorLastName = authorLastName;
    return this;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public CocktailDetailsViewModel setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
    return this;
  }

  public SpiritNameEnum getSpirit() {
    return spirit;
  }

  public CocktailDetailsViewModel setSpirit(SpiritNameEnum spirit) {
    this.spirit = spirit;
    return this;
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  public CocktailDetailsViewModel setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
    return this;
  }

  public Integer getPercentAlcohol() {
    return percentAlcohol;
  }

  public CocktailDetailsViewModel setPercentAlcohol(Integer percentAlcohol) {
    this.percentAlcohol = percentAlcohol;
    return this;
  }

  public Integer getServings() {
    return servings;
  }

  public CocktailDetailsViewModel setServings(Integer servings) {
    this.servings = servings;
    return this;
  }

  public List<String> getIngredients() {
    return ingredients;
  }

  public CocktailDetailsViewModel setIngredients(List<String> ingredients) {
    this.ingredients = ingredients;
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

  public boolean isCanDelete() {
    return canDelete;
  }

  public CocktailDetailsViewModel setCanDelete(boolean canDelete) {
    this.canDelete = canDelete;
    return this;
  }

  public boolean isFavorite() {
    return isFavorite;
  }

  public CocktailDetailsViewModel setFavorite(boolean favorite) {
    isFavorite = favorite;
    return this;
  }

  public TypeNameEnum getType() {
    return type;
  }

  public CocktailDetailsViewModel setType(TypeNameEnum type) {
    this.type = type;
    return this;
  }
}
