package com.signup.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
  private int id;
  private String firstName;
  private String lastName;
  private String contactNo;
  private String emailId;
  @JsonIgnore
  private String password;
  @JsonIgnore
  private String confirmPassword;
}
