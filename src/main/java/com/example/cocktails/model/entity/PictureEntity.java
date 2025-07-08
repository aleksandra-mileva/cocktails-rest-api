package com.example.cocktails.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pictures")
public class PictureEntity extends BaseEntity {

  @NotNull
  private String url;

  private String publicId;

  private String title;

  @ManyToOne
  @NotNull
  private UserEntity author;

  public String getUrl() {
    return url;
  }

  public PictureEntity setUrl(String url) {
    this.url = url;
    return this;
  }

  public String getPublicId() {
    return publicId;
  }

  public PictureEntity setPublicId(String publicId) {
    this.publicId = publicId;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public PictureEntity setTitle(String title) {
    this.title = title;
    return this;
  }

  public UserEntity getAuthor() {
    return author;
  }

  public PictureEntity setAuthor(UserEntity author) {
    this.author = author;
    return this;
  }
}
