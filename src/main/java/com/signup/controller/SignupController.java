package com.signup.controller;

import com.signup.dto.UserResponse;
import com.signup.dto.UserRequest;
import com.signup.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/webapp/user/signup")
public class SignupController {
  @Autowired private SignUpService signUpService;

  @PostMapping("/save")
  public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest userRequest) {
    return new ResponseEntity<UserResponse>(
        signUpService.saveUser(userRequest), HttpStatus.CREATED);
  }

  @GetMapping("/getUser/{id}")
  public ResponseEntity<UserResponse> getUserById(@PathVariable int id) {
    return new ResponseEntity<UserResponse>(signUpService.getUserById(id), HttpStatus.OK);
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserResponse>> getUsers() {
    return new ResponseEntity<List<UserResponse>>(signUpService.getUsers(), HttpStatus.OK);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<UserResponse> updateUser(
      @RequestBody UserRequest userRequest, @PathVariable int id) {
    return new ResponseEntity<UserResponse>(
        signUpService.updateUser(userRequest, id), HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable int id) {
    return new ResponseEntity<String>(signUpService.deleteUser(id), HttpStatus.OK);
  }

  @GetMapping("sort/{sortBy}")
  public ResponseEntity<List<UserResponse>> sortUsers(@PathVariable String sortBy){
    return new ResponseEntity<List<UserResponse>>(signUpService.sortUsers(sortBy),HttpStatus.OK);
  }
}
