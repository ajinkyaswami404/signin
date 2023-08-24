package com.signup.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorMessage {
  private HttpStatus statusCode;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timeStamp;

  private String errorMessage;
  private String localizedMessage;

  public ErrorMessage() {
    timeStamp = LocalDateTime.now();
  }

  public ErrorMessage(HttpStatus statusCode) {
    this();
    this.statusCode = statusCode;
  }

  public ErrorMessage(HttpStatus statusCode, Throwable ex) {
    this();
    this.statusCode = statusCode;
    this.errorMessage = ex.getMessage();
    this.localizedMessage = ex.getLocalizedMessage();
  }

  public ErrorMessage(HttpStatus statusCode, String errorMessage, Throwable ex) {
    this();
    this.statusCode = statusCode;
    this.errorMessage = errorMessage;
    this.localizedMessage = ex.getLocalizedMessage();
  }

  public HttpStatus getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(HttpStatus statusCode) {
    this.statusCode = statusCode;
  }

  public LocalDateTime getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(LocalDateTime timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
