package com.example.cocktails.web.exception;

import java.util.Map;

public class UniqueUserException extends RuntimeException {

  private final Map<String, String> fieldsAndMessages;

  public UniqueUserException(Map<String, String> fieldsAndMessages) {
    this.fieldsAndMessages = fieldsAndMessages;
  }

  public Map<String, String> getFieldsAndMessages() {
    return fieldsAndMessages;
  }
}
