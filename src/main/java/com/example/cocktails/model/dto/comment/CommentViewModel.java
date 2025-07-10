package com.example.cocktails.model.dto.comment;

import java.time.LocalDateTime;

public class CommentViewModel {

  private Long id;
  private String text;
  private String author;
  private LocalDateTime created;
  private boolean canDelete;

  public CommentViewModel(Long id, String text, String authorFirstName,
      String authorLastName, LocalDateTime created) {
    this.id = id;
    this.text = text;
    this.author = authorFirstName + " " + authorLastName;
    this.created = created;
  }

  public Long getId() {
    return id;
  }

  public CommentViewModel setId(Long id) {
    this.id = id;
    return this;
  }

  public String getText() {
    return text;
  }

  public CommentViewModel setText(String text) {
    this.text = text;
    return this;
  }

  public String getAuthor() {
    return author;
  }

  public CommentViewModel setAuthor(String author) {
    this.author = author;
    return this;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public CommentViewModel setCreated(LocalDateTime created) {
    this.created = created;
    return this;
  }

  public boolean isCanDelete() {
    return canDelete;
  }

  public CommentViewModel setCanDelete(boolean canDelete) {
    this.canDelete = canDelete;
    return this;
  }
}
