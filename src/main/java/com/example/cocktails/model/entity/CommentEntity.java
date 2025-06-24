package com.example.cocktails.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity {

  @NotNull
  private String text;

  @NotNull
  private LocalDateTime created;

  @ManyToOne
  @NotNull
  private UserEntity author;

  @ManyToOne
  @NotNull
  private CocktailEntity cocktail;

  public String getText() {
    return text;
  }

  public CommentEntity setText(String text) {
    this.text = text;
    return this;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public CommentEntity setCreated(LocalDateTime created) {
    this.created = created;
    return this;
  }

  public UserEntity getAuthor() {
    return author;
  }

  public CommentEntity setAuthor(UserEntity author) {
    this.author = author;
    return this;
  }

  public CocktailEntity getCocktail() {
    return cocktail;
  }

  public CommentEntity setCocktail(CocktailEntity cocktail) {
    this.cocktail = cocktail;
    return this;
  }
}
