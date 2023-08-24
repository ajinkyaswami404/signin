package com.signup.constant;

public final class ExceptionMessages {
  public static final String invalidFirstName =
      "user's first name should not contain any special character or digits. "
          + "only alphabets and whitespace is allowed & first letter should be capital only. "
          + "min 1 & max 19 letters allowed. ";
  public static final String invalidLastName =
      "user's last name should not contain any special character or digits. "
          + "only alphabets and whitespace is allowed & first letter must be capital only. "
          + "min 1 & max 19 letters allowed. ";
  public static final String invalidContactNo =
      "user's contact number can contain min 10 max 14 digits including + / whitespace / - "
          + "can start with 0 / +91 / any valid indian mobile number. ";
  public static final String invalidEmailId =
      "user's email id format must be name + @ + domainName. ";
  public static final String invalidPassword =
      "user's password must contain Minimum eight characters "
          + "at least one letter, one number and one special character. ";
  public static final String invalidConfirmPassword =
      "user's confirm password must contain Minimum eight characters "
          + "at least one letter, one number and one special character "
          + "it must be exact same as password. ";
  public static final String passwordMismatch =
      "user's password and confirm password must be exact same. ";
  public static final String userNotFound = "user not found for provided details. ";
  public static final String userAlreadyExist =
      "user is already exist in database for provided details. ";
  public static final String invalidUser =
      "user should be valid. any field can not be empty / blank / null. ";
}
