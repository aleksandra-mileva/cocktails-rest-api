package com.example.cocktails.model.validation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExceptionResponseDTO {

  private List<String> messages;
  private LocalDateTime dateTime;

  public ExceptionResponseDTO() {
    messages = new ArrayList<>();
  }

  public List<String> getMessages() {
    return messages;
  }

  public void setMessages(List<String> messages) {
    this.messages = messages;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }
}
