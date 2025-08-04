package com.example.cocktails.web.exception;

public class InvalidFileException extends RuntimeException {

  private final String file;

  public InvalidFileException(String file, String message) {
    super(message);
    this.file = file;
  }

  public String getFile() {
    return file;
  }
}
