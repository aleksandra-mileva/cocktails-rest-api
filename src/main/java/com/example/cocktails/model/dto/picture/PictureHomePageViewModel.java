package com.example.cocktails.model.dto.picture;

public class PictureHomePageViewModel {

  private String url;
  private Long cocktailId;
  String authorFullName;

  public String getUrl() {
    return url;
  }

  public PictureHomePageViewModel setUrl(String url) {
    this.url = url;
    return this;
  }

  public Long getCocktailId() {
    return cocktailId;
  }

  public PictureHomePageViewModel setCocktailId(Long cocktailId) {
    this.cocktailId = cocktailId;
    return this;
  }

  public String getAuthorFullName() {
    return authorFullName;
  }

  public PictureHomePageViewModel setAuthorFullName(String authorFullName) {
    this.authorFullName = authorFullName;
    return this;
  }
}
