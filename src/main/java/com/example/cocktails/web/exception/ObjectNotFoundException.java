package com.example.cocktails.web.exception;

public class ObjectNotFoundException extends RuntimeException {

  private final String object;

  public ObjectNotFoundException(String object, String message) {
    super(message);
    this.object = object;
  }

  public String getObject() {
    return object;
  }
}
