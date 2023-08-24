package com.signup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.signup.controller.SignupController;
import com.signup.dto.UserRequest;
import com.signup.dto.UserResponse;
import com.signup.entity.User;
import com.signup.error.ErrorMessage;
import com.signup.service.SignUpService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SignupControllerTest {
  @Autowired private MockMvc mockMvc;
  private ObjectMapper objectMapper;
  private ErrorMessage errorMessage;
  @Mock private SignUpService signUpService;
  @InjectMocks private SignupController signupController;
  private UserRequest userRequest1;
  private UserRequest userRequest2;
  private UserRequest userRequest3;
  List<UserResponse> userResponseList;

  @BeforeEach
  public void setUpRequest() {
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

    userResponseList = new ArrayList<>();

    userResponseList.add(
        UserResponse.builder()
            .id(userRequest1.getId())
            .firstName(userRequest1.getFirstName())
            .lastName(userRequest1.getLastName())
            .contactNo(userRequest1.getContactNo())
            .emailId(userRequest1.getEmailId())
            .build());
    userResponseList.add(
        UserResponse.builder()
            .id(userRequest2.getId())
            .firstName(userRequest2.getFirstName())
            .lastName(userRequest2.getLastName())
            .contactNo(userRequest2.getContactNo())
            .emailId(userRequest2.getEmailId())
            .build());
    userResponseList.add(
        UserResponse.builder()
            .id(userRequest3.getId())
            .firstName(userRequest3.getFirstName())
            .lastName(userRequest3.getLastName())
            .contactNo(userRequest3.getContactNo())
            .emailId(userRequest3.getEmailId())
            .build());

    errorMessage = new ErrorMessage();

    objectMapper = new ObjectMapper();

    mockMvc = MockMvcBuilders.standaloneSetup(signupController).build();
  }

  @AfterEach
  public void tearDown() {
    userRequest1 = userRequest2 = userRequest3 = null;
    userResponseList = null;
  }

  @Test
  @DisplayName("Test POST mapping to save user")
  public void testPostMappingSaveUser() throws Exception {
    String json = "{}";
    when(signUpService.saveUser(any(UserRequest.class)))
        .thenReturn(
            UserResponse.builder()
                .id(userRequest1.getId())
                .firstName(userRequest1.getFirstName())
                .lastName(userRequest1.getLastName())
                .contactNo(userRequest1.getContactNo())
                .emailId(userRequest1.getEmailId())
                .password(userRequest1.getPassword())
                .confirmPassword(userRequest1.getConfirmPassword())
                .build());
    try {
      json = objectMapper.writeValueAsString(userRequest1);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    mockMvc
        .perform(
            post("/webapp/user/signup/save").contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(userRequest1.getId()))
        .andExpect(jsonPath("$.firstName").value(userRequest1.getFirstName()))
        .andExpect(jsonPath("$.lastName").value(userRequest1.getLastName()))
        .andExpect(jsonPath("$.contactNo").value(userRequest1.getContactNo()))
        .andExpect(jsonPath("$.emailId").value(userRequest1.getEmailId()))
        .andDo(MockMvcResultHandlers.print());
    verify(signUpService, times(1)).saveUser(any());
  }

  @Test
  @DisplayName("Test GET user by id")
  public void testGetUserById() throws Exception {
    when(signUpService.getUserById(anyInt()))
        .thenReturn(
            UserResponse.builder()
                .id(userRequest1.getId())
                .firstName(userRequest1.getFirstName())
                .lastName(userRequest1.getLastName())
                .contactNo(userRequest1.getContactNo())
                .emailId(userRequest1.getEmailId())
                .build());

    String jsonString = "{}";

    try {
      jsonString = objectMapper.writeValueAsString(userRequest1);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    mockMvc
        .perform(
            get("/webapp/user/signup/getUser/{id}", 1)
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(userRequest1.getId()))
        .andExpect(jsonPath("$.firstName").value(userRequest1.getFirstName()))
        .andExpect(jsonPath("$.lastName").value(userRequest1.getLastName()))
        .andExpect(jsonPath("$.contactNo").value(userRequest1.getContactNo()))
        .andExpect(jsonPath("$.emailId").value(userRequest1.getEmailId()))
        .andDo(MockMvcResultHandlers.print());

    verify(signUpService, times(1)).getUserById(anyInt());
  }

  @Test
  @DisplayName("Test GET list of all users")
  public void testGetAllUsers() throws Exception {

    when(signUpService.getUsers()).thenReturn(userResponseList);

    mockMvc
        .perform(get("/webapp/user/signup/users"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(userResponseList)))
        .andDo(MockMvcResultHandlers.print());

    verify(signUpService, times(1)).getUsers();
  }

  @Test
  @DisplayName("Test PUT mapping method for update user")
  public void testPatchMappingUpdateUser() throws Exception {
    String s = "Namane";
    User user = new User();
    user.setLastName(s);
    userRequest1.setLastName(s);
    String jsonString = "{}";

    try {
      jsonString = objectMapper.writeValueAsString(userRequest1);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    when(signUpService.updateUser(any(UserRequest.class), anyInt()))
        .thenReturn(
            UserResponse.builder()
                .id(userRequest1.getId())
                .firstName(userRequest1.getFirstName())
                .lastName(userRequest1.getLastName())
                .contactNo(userRequest1.getContactNo())
                .emailId(userRequest1.getEmailId())
                .build());

    mockMvc
        .perform(
            put("/webapp/user/signup/update/{id}", 1)
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(userRequest1.getId()))
        .andExpect(jsonPath("$.firstName").value(userRequest1.getFirstName()))
        .andExpect(jsonPath("$.lastName").value(userRequest1.getLastName()))
        .andExpect(jsonPath("$.contactNo").value(userRequest1.getContactNo()))
        .andExpect(jsonPath("$.emailId").value(userRequest1.getEmailId()))
        .andDo(MockMvcResultHandlers.print());

    verify(signUpService, times(1)).updateUser(any(UserRequest.class), anyInt());
  }

  @Test
  @DisplayName("Test DELETE mapping method for delete user")
  public void testDeleteMappingDeleteUserMethod() throws Exception {
    when(signUpService.deleteUser(anyInt())).thenReturn("user deleted successfully for id " + 1);

    mockMvc
        .perform(
            delete("/webapp/user/signup/delete/{id}", 1).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print());

    verify(signUpService, times(1)).deleteUser(anyInt());
  }

  @Test
  @DisplayName("Test sorting method for user")
  public void testSortMethod() throws Exception {
    when(signUpService.sortUsers(anyString())).thenReturn(userResponseList);
    mockMvc
        .perform(
            get("/webapp/user/signup/sort/{sortBy}", "firstName")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print());
    verify(signUpService, times(1)).sortUsers(anyString());
  }
}
