package com.example.cocktails.model.dto.picture;

public class PictureViewModel {

  private Long id;
  private String url;
  private Long cocktailId;

  public Long getId() {
    return id;
  }

  public PictureViewModel setId(Long id) {
    this.id = id;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public PictureViewModel setUrl(String url) {
    this.url = url;
    return this;
  }

  public Long getCocktailId() {
    return cocktailId;
  }

  public PictureViewModel setCocktailId(Long cocktailId) {
    this.cocktailId = cocktailId;
    return this;
  }
}
