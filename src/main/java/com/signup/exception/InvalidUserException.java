package com.signup.exception;

public class InvalidUserException extends RuntimeException {
  public InvalidUserException(String message) {
    super(message);
  }
}
