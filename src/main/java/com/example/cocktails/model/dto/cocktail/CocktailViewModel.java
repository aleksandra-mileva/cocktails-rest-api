package com.example.cocktails.model.dto.cocktail;

import com.example.cocktails.model.entity.enums.FlavourEnum;
import com.example.cocktails.model.entity.enums.SpiritNameEnum;

public class CocktailViewModel {

  private Long id;
  private String name;
  private FlavourEnum flavour;
  private SpiritNameEnum spirit;
  private String author;
  private String pictureUrl;
  private int percentAlcohol;
  private int servings;

  public CocktailViewModel(Long id, String name, FlavourEnum flavour, SpiritNameEnum spirit, String authorFirstName,
      String authorLastName, String pictureUrl, int percentAlcohol, int servings) {
    this.id = id;
    this.name = name;
    this.flavour = flavour;
    this.spirit = spirit;
    this.author = authorFirstName + " " + authorLastName;
    this.pictureUrl = pictureUrl;
    this.percentAlcohol = percentAlcohol;
    this.servings = servings;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public CocktailViewModel setName(String name) {
    this.name = name;
    return this;
  }

  public SpiritNameEnum getSpirit() {
    return spirit;
  }

  public void setSpirit(SpiritNameEnum spirit) {
    this.spirit = spirit;
  }

  public FlavourEnum getFlavour() {
    return flavour;
  }

  public CocktailViewModel setFlavour(FlavourEnum flavour) {
    this.flavour = flavour;
    return this;
  }

  public String getAuthor() {
    return author;
  }

  public CocktailViewModel setAuthor(String author) {
    this.author = author;
    return this;
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  public CocktailViewModel setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
    return this;
  }

  public int getPercentAlcohol() {
    return percentAlcohol;
  }

  public CocktailViewModel setPercentAlcohol(int percentAlcohol) {
    this.percentAlcohol = percentAlcohol;
    return this;
  }

  public int getServings() {
    return servings;
  }

  public CocktailViewModel setServings(int servings) {
    this.servings = servings;
    return this;
  }
}
