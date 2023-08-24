package com.signup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.signup.converter.UserConverter;
import com.signup.dto.UserRequest;
import com.signup.dto.UserResponse;
import com.signup.entity.User;
import com.signup.exception.UserAlreadyExistException;
import com.signup.repository.UserRepository;
import com.signup.service.SignUpService;
import com.signup.util.ValidateUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SignupServiceTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Mock private UserRepository userRepository;
  @Mock private UserConverter userConverter;
  @Mock private ValidateUser validateUser;
  @Autowired @InjectMocks private SignUpService signUpService;
  private User user;
  private UserRequest userRequest1;
  private UserRequest userRequest2;
  private UserRequest userRequest3;
  private UserResponse userResponse;
  private UserResponse userResponse1;

  @BeforeEach
  public void setupUsers() {
    userRequest1 =
        UserRequest.builder()
            .id(1)
            .firstName("Shreyas")
            .lastName("Mane")
            .contactNo("+91-7845963211")
            .emailId("shreyas@gmail.com")
            .password("shreyas.mane@1234")
            .confirmPassword("shreyas.mane@1234")
            .build();

    userRequest2 =
        UserRequest.builder()
            .id(2)
            .firstName("Vijay")
            .lastName("Shinde")
            .contactNo("+91-8874569912")
            .emailId("vijay@gmail.com")
            .password("vijay.shinde@123")
            .confirmPassword("vijay.shinde@123")
            .build();

    userRequest3 =
        UserRequest.builder()
            .id(3)
            .firstName("Viraj")
            .lastName("Jagtap")
            .contactNo("+91-8874678102")
            .emailId("viraj@gmail.com")
            .password("viraj.jtp@123")
            .confirmPassword("viraj.jtp@123")
            .build();
    user =
        User.builder()
            .id(userRequest1.getId())
            .firstName(userRequest1.getFirstName())
            .lastName(userRequest1.getLastName())
            .contactNo(userRequest1.getContactNo())
            .emailId(userRequest1.getEmailId())
            .password(userRequest1.getPassword())
            .build();

    userResponse =
        UserResponse.builder()
            .id(userRequest1.getId())
            .firstName(userRequest1.getFirstName())
            .lastName(userRequest1.getLastName())
            .contactNo(userRequest1.getContactNo())
            .emailId(userRequest1.getEmailId())
            .password(userRequest1.getPassword())
            .build();

    userResponse1 = userResponse;
  }

  @AfterEach
  public void tearDown() {
    userRequest1 = userRequest2 = userRequest3 = null;
    user = null;
    userResponse = userResponse1 = null;
  }

  @Test
  @DisplayName("Test save user and returns saved user.")
  public void testSaveUser() throws Exception {
    when(userRepository.save(user)).thenReturn(user);
    when(validateUser.isValid(any())).thenReturn(" valid");
    when(validateUser.isDuplicate(any())).thenReturn("empty");
    when(signUpService.saveUser(userRequest1)).thenReturn(userResponse);
    assertEquals(userResponse, userResponse1);
    verify(userRepository, times(1)).save(any());
  }

  @Test
  @DisplayName("Inserting same user again throws UserAlreadyExistException")
  public void testAddSameUser() {
    when(validateUser.isValid(any())).thenReturn(" valid");
    when(validateUser.isDuplicate(any())).thenReturn("not empty");
    assertThrows(
        UserAlreadyExistException.class,
        () -> {
          signUpService.saveUser(userRequest1);
        });
  }
}
