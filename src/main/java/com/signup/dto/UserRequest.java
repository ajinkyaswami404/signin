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
public class UserRequest {
  @JsonIgnore
  private int id;
  private String firstName;
  private String lastName;
  private String contactNo;
  private String emailId;
  private String password;
  private String confirmPassword;
}
