package com.signup.util;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidatePassword {
  private static final Pattern pattern =
      Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
                            //Minimum eight characters, at least one letter, one number and one special character:
  public boolean isValidPassword(String password) {
    Matcher matcher = pattern.matcher(password);
    return matcher.matches();
  }
}
