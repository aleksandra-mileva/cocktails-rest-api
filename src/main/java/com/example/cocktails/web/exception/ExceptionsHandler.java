package com.example.cocktails.web.exception;

import com.example.cocktails.model.validation.ExceptionResponseDTO;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionsHandler {

  private final MessageSource messageSource;

  public ExceptionsHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(InvalidFileException.class)
  public ResponseEntity<ExceptionResponseDTO> handleInvalidFileException(InvalidFileException ex) {
    return getResponseEntity(ex, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<ExceptionResponseDTO> handleInvalidTokenException(InvalidTokenException ex) {
    ExceptionResponseDTO response = new ExceptionResponseDTO();
    response.setDateTime(LocalDateTime.now());
    response.getMessages().add(
        messageSource.getMessage("user.registration.verification.invalid.token", null, LocaleContextHolder.getLocale())
    );
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<ExceptionResponseDTO> handleObjectNotFoundException(ObjectNotFoundException ex) {

    return getResponseEntity(ex, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponseDTO> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex){

    List<String> errors = new ArrayList<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String message = error.getDefaultMessage();
      errors.add(fieldName + ": " + message);
    });

    ExceptionResponseDTO response = new ExceptionResponseDTO();
    response.setDateTime(LocalDateTime.now());
    response.setMessages(errors);

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ExceptionResponseDTO> handleIllegalArgumentException(
      IllegalArgumentException ex) {

    return getResponseEntity(ex, HttpStatus.CONFLICT);
  }

  private ResponseEntity<ExceptionResponseDTO> getResponseEntity(Exception exception, HttpStatus httpStatus){
    ExceptionResponseDTO response = new ExceptionResponseDTO();
    response.setDateTime(LocalDateTime.now());
    response.getMessages().add(exception.getMessage());

    return new ResponseEntity<>(response, httpStatus);
  }
}
