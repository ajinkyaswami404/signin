package com.signup.converter;

import com.signup.dto.UserRequest;
import com.signup.dto.UserResponse;
import com.signup.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

  public User requestToUser(UserRequest userRequest) {
    return User.builder()
        .id(userRequest.getId())
        .firstName(userRequest.getFirstName())
        .lastName(userRequest.getLastName())
        .contactNo(userRequest.getContactNo())
        .emailId(userRequest.getEmailId())
        .password(userRequest.getPassword())
        .confirmPassword(userRequest.getConfirmPassword())
        .build();
  }

  public UserResponse userToResponse(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .contactNo(user.getContactNo())
        .emailId(user.getEmailId())
        .password(user.getPassword())
        .confirmPassword(user.getConfirmPassword())
        .build();
  }

  public List<UserResponse> userToUserResponseList(List<User> userList) {
    return userList.stream().map(x -> userToResponse(x)).collect(Collectors.toList());
  }

  public List<User> RequestToUserList(List<UserRequest> userRequestList) {
    return userRequestList.stream().map(x -> requestToUser(x)).collect(Collectors.toList());
  }

  public Page<UserResponse> userToResponsePage(Page<User> userPage) {
    return userPage.map(this::userToResponse);
  }
}
