package com.example.cocktails.model.dto.cocktail;

public class CocktailHomePageViewModel {

  private Long id;
  private String pictureUrl;
  String authorFullName;

  public CocktailHomePageViewModel(Long id, String pictureUrl, String authorFirstName,
      String authorLastName) {
    this.id = id;
    this.pictureUrl = pictureUrl;
    this.authorFullName = authorFirstName + " " + authorLastName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  public String getAuthorFullName() {
    return authorFullName;
  }

  public void setAuthorFullName(String authorFullName) {
    this.authorFullName = authorFullName;
  }
}
