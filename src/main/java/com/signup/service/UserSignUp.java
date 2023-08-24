package com.signup.service;

import com.signup.dto.UserRequest;
import com.signup.dto.UserResponse;

import java.util.List;

public interface UserSignUp {
  public UserResponse saveUser(UserRequest userRequest);

  public UserResponse getUserById(int userId);

  public List<UserResponse> getUsers();

  public UserResponse updateUser(UserRequest userRequest, int userId);

  public String deleteUser(int userId);

  public List<UserResponse> sortUsers(String sortBy);
}
