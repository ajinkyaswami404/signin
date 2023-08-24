package com.signup.util;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidateNames {
  private static final Pattern pattern=Pattern.compile("^[A-Z](?=.{1,19}$)[A-Za-z]*(?:\\h+[A-Z][A-Za-z]*)*$");
  //"^[A-Z](?=.{1,119}$)[A-Za-z]*(?:\\h+[A-Z][A-Za-z]*)*$"
    //"^[ A-Za-z]+$"
  public boolean isValidName(String name){
      Matcher matcher=pattern.matcher(name);
      return matcher.matches();
  }
}
