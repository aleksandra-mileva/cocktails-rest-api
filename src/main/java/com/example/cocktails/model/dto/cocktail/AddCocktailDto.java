package com.example.cocktails.model.dto.cocktail;

import com.example.cocktails.model.entity.enums.FlavourEnum;
import com.example.cocktails.model.entity.enums.SpiritNameEnum;
import com.example.cocktails.model.entity.enums.TypeNameEnum;
import com.example.cocktails.model.validation.AtLeastOneFile;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class AddCocktailDto {

  @AtLeastOneFile
  private List<MultipartFile> pictureFiles;
  @NotEmpty
  @Size(min = 5, max = 30)
  private String name;
  @NotEmpty
  private String ingredients;
  @NotEmpty
  private String preparation;
  @NotNull
  private FlavourEnum flavour;
  private String videoUrl;
  private TypeNameEnum type;
  @NotNull
  private SpiritNameEnum spirit;
  @NotNull
  private Integer percentAlcohol;
  @NotNull
  @Positive
  private Integer servings;

  public AddCocktailDto() {
    this.pictureFiles = new ArrayList<>();
  }

  public List<MultipartFile> getPictureFiles() {
    return pictureFiles;
  }

  public AddCocktailDto setPictureFiles(List<MultipartFile> pictureFiles) {
    this.pictureFiles = pictureFiles;
    return this;
  }

  public String getName() {
    return name;
  }

  public AddCocktailDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getIngredients() {
    return ingredients;
  }

  public AddCocktailDto setIngredients(String ingredients) {
    this.ingredients = ingredients;
    return this;
  }

  public String getPreparation() {
    return preparation;
  }

  public AddCocktailDto setPreparation(String preparation) {
    this.preparation = preparation;
    return this;
  }

  public FlavourEnum getFlavour() {
    return flavour;
  }

  public AddCocktailDto setFlavour(FlavourEnum flavour) {
    this.flavour = flavour;
    return this;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public AddCocktailDto setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
    return this;
  }

  public TypeNameEnum getType() {
    return type;
  }

  public AddCocktailDto setType(TypeNameEnum type) {
    this.type = type;
    return this;
  }

  public SpiritNameEnum getSpirit() {
    return spirit;
  }

  public AddCocktailDto setSpirit(SpiritNameEnum spirit) {
    this.spirit = spirit;
    return this;
  }

  public Integer getPercentAlcohol() {
    return percentAlcohol;
  }

  public AddCocktailDto setPercentAlcohol(Integer percentAlcohol) {
    this.percentAlcohol = percentAlcohol;
    return this;
  }

  public Integer getServings() {
    return servings;
  }

  public AddCocktailDto setServings(Integer servings) {
    this.servings = servings;
    return this;
  }
}
