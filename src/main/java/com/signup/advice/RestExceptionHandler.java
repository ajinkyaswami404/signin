package com.signup.advice;

import com.signup.error.ErrorMessage;
import com.signup.exception.InvalidUserException;
import com.signup.exception.PasswordMismatchException;
import com.signup.exception.UserAlreadyExistException;
import com.signup.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  public ResponseEntity handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders header,
      HttpStatus status,
      WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage();
    errorMessage.setStatusCode(status);
    errorMessage.setErrorMessage("Required request body is missing");
    return new ResponseEntity(errorMessage, errorMessage.getStatusCode());
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException exception) {
    ErrorMessage errorMessage = new ErrorMessage();
    errorMessage.setErrorMessage(exception.getMessage());
    errorMessage.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(errorMessage, errorMessage.getStatusCode());
  }

  @ExceptionHandler(UserAlreadyExistException.class)
  public ResponseEntity<ErrorMessage> handleUserAlreadyExistsException(
      UserAlreadyExistException exception) {
    ErrorMessage errorMessage = new ErrorMessage();
    errorMessage.setErrorMessage(exception.getMessage());
    errorMessage.setStatusCode(HttpStatus.CONFLICT);
    return new ResponseEntity<>(errorMessage, errorMessage.getStatusCode());
  }

  @ExceptionHandler(InvalidUserException.class)
  public ResponseEntity<ErrorMessage> handleInvalidUserException(InvalidUserException exception) {
    ErrorMessage errorMessage = new ErrorMessage();
    errorMessage.setErrorMessage(exception.getMessage());
    errorMessage.setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY);
    return new ResponseEntity<>(errorMessage, errorMessage.getStatusCode());
  }

  @ExceptionHandler(PasswordMismatchException.class)
  public ResponseEntity<ErrorMessage> handlePasswordMismatchException(
      PasswordMismatchException exception) {
    ErrorMessage errorMessage = new ErrorMessage();
    errorMessage.setErrorMessage(exception.getMessage());
    errorMessage.setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY);
    return new ResponseEntity<>(errorMessage, errorMessage.getStatusCode());
  }
}
