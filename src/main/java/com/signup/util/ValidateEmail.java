package com.signup.util;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidateEmail {
  private static final Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

  public boolean isValidEmail(String email) {
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }
}
