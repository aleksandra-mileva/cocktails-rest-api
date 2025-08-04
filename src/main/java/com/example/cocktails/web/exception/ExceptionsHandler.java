package com.example.cocktails.web.exception;

import com.example.cocktails.model.validation.ExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionsHandler {

  @ExceptionHandler(InvalidFileException.class)
  public ResponseEntity<ExceptionResponseDTO> handleInvalidFileException(InvalidFileException ex) {
    ExceptionResponseDTO response = new ExceptionResponseDTO();
    response.setDateTime(LocalDateTime.now());
    response.addError(ex.getFile(), ex.getMessage());

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<ExceptionResponseDTO> handleInvalidTokenException(InvalidTokenException ex) {
    ExceptionResponseDTO response = new ExceptionResponseDTO();
    response.setDateTime(LocalDateTime.now());
    response.addError("token", "Token is expired or is invalid. Please provide a valid token.");

    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<ExceptionResponseDTO> handleObjectNotFoundException(ObjectNotFoundException ex) {
    ExceptionResponseDTO response = new ExceptionResponseDTO();
    response.setDateTime(LocalDateTime.now());
    response.addError(ex.getObject(), ex.getMessage());

    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponseDTO> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    ExceptionResponseDTO response = new ExceptionResponseDTO();
    response.setDateTime(LocalDateTime.now());

    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      response.addError(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ExceptionResponseDTO> handleIllegalArgumentException(
      IllegalArgumentException ex) {

    ExceptionResponseDTO response = new ExceptionResponseDTO();
    response.setDateTime(LocalDateTime.now());

    response.addError("argument", ex.getMessage());


    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ExceptionResponseDTO> handleUsernameNotFoundException(UsernameNotFoundException ex) {
    ExceptionResponseDTO response = new ExceptionResponseDTO();
    response.setDateTime(LocalDateTime.now());
    response.addError("username", ex.getMessage());

    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UsernameChangedException.class)
  public ResponseEntity<ExceptionResponseDTO> handleUsernameChangedException(UsernameChangedException ex) {
    ExceptionResponseDTO response = new ExceptionResponseDTO();
    response.setDateTime(LocalDateTime.now());
    response.addError("username", ex.getMessage());

    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }
}
