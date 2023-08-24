package com.signup.service;

import com.signup.constant.ExceptionMessages;
import com.signup.constant.Fields;
import com.signup.constant.LogsAndUtilConstants;
import com.signup.converter.UserConverter;
import com.signup.dto.UserRequest;
import com.signup.dto.UserResponse;
import com.signup.entity.User;
import com.signup.exception.InvalidUserException;
import com.signup.exception.PasswordMismatchException;
import com.signup.exception.UserAlreadyExistException;
import com.signup.exception.UserNotFoundException;
import com.signup.repository.UserRepository;
import com.signup.util.ValidateEmail;
import com.signup.util.ValidateUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class SignUpService implements UserSignUp {
  @Autowired private UserRepository userRepository;
  @Autowired private ValidateUser validateUser;
  @Autowired private UserConverter userConverter;
  @Autowired private ValidateEmail validateEmail;
  private static final Logger logger = LogManager.getLogger(ValidateUser.class);

  @Override
  public UserResponse saveUser(UserRequest userRequest) {
    User user = userConverter.requestToUser(userRequest);
    if (user.getPassword().equals(user.getConfirmPassword())) {
      String validate = validateUser.isValid(user);
      if (validate.equals(Fields.valid)) {
        if (validateUser.isDuplicate(user).equals(Fields.empty)) {
          user = userRepository.save(user);
          logger.info(LogsAndUtilConstants.saveLog + user);
        } else {
          logger.info(LogsAndUtilConstants.duplicateUserLog + user);
          throw new UserAlreadyExistException(ExceptionMessages.userAlreadyExist + user);
        }
      } else {
        logger.info(LogsAndUtilConstants.invalidUserLog + user);
        throw new InvalidUserException(ExceptionMessages.invalidUser + validate + user);
      }
    } else {
      throw new PasswordMismatchException(ExceptionMessages.passwordMismatch);
    }
    return userConverter.userToResponse(user);
  }

  @Override
  public UserResponse getUserById(int id) {
    try {
      User user = userRepository.findById(id).get();
      logger.info(LogsAndUtilConstants.userFoundLog + id);
      return userConverter.userToResponse(user);
    } catch (NoSuchElementException e) {
      logger.info(ExceptionMessages.userNotFound + id, e);
      throw new UserNotFoundException(ExceptionMessages.userNotFound + id);
    }
  }

  @Override
  public List<UserResponse> getUsers() {
    try {
      if (!userRepository.findAll().isEmpty()) {
        logger.info(LogsAndUtilConstants.usersFoundLog);
      }
      return userConverter.userToUserResponseList(userRepository.findAll());
    } catch (NoSuchElementException e) {
      logger.error(ExceptionMessages.userNotFound, e);
      throw new UserNotFoundException(ExceptionMessages.userNotFound);
    }
  }

  @Override
  public UserResponse updateUser(UserRequest userRequest, int id) {
    User user =
        User.builder()
            .id(id)
            .firstName(userRequest.getFirstName())
            .lastName(userRequest.getLastName())
            .contactNo(userRequest.getContactNo())
            .emailId(userRequest.getEmailId())
            .password(userRequest.getPassword())
            .confirmPassword(userRequest.getConfirmPassword())
            .build();
    String validate = validateUser.isValid(user);
    if (validate.equals(Fields.valid)) {
      List<User> userUpdate =
          userRepository.findUser(
              user.getFirstName(),
              user.getLastName(),
              user.getEmailId(),
              user.getContactNo(),
              user.getPassword());
      if (!userUpdate.isEmpty()) {
        throw new UserAlreadyExistException(ExceptionMessages.userAlreadyExist + user);
      } else {
        logger.info(LogsAndUtilConstants.userUpdateLog + user);
        return userConverter.userToResponse(userRepository.save(user));
      }
    } else {
      throw new InvalidUserException(ExceptionMessages.invalidUser + user);
    }
  }

  @Override
  public String deleteUser(int id) {
    try {
      if (userRepository.existsById(id)) {
        userRepository.deleteById(id);
        logger.info(LogsAndUtilConstants.userDeleteLog + id);
      }
      return LogsAndUtilConstants.userDeleteLog + id;
    } catch (NoSuchElementException e) {
      throw new UserNotFoundException(ExceptionMessages.userNotFound + id);
    }
  }

  @Override
  public List<UserResponse> sortUsers(String sortBy) {
    return userConverter.userToUserResponseList(userRepository.findAll(Sort.by(sortBy)));
  }
}
