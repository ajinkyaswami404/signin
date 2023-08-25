package com.signup.util;

import static org.springframework.util.ObjectUtils.isEmpty;

import com.signup.constant.ExceptionMessages;
import com.signup.constant.Fields;
import com.signup.entity.User;
import com.signup.repository.UserRepository;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateUser {
  @Autowired private UserRepository userRepository;
  @Autowired private ValidateNames validateNames;
  @Autowired private ValidateContactNo validateContactNo;
  @Autowired private ValidateEmail validateEmail;
  @Autowired private ValidatePassword validatePassword;
  private static final Logger logger = LogManager.getLogger(ValidateUser.class);

  public String isValid(User user) {
    int count = 0, i = 1;
    String returnMessage = "";

    switch (i) {
      case 1:
        if (isEmpty(user.getFirstName())
            || user.getFirstName().isBlank()
            || !validateNames.isValidName(user.getFirstName())) {
          logger.warn(Fields.invalid + Fields.firstName + Fields.for_ + user);
          count++;
          i++;
          returnMessage = returnMessage + ExceptionMessages.invalidFirstName;
        }
      case 2:
        if (isEmpty(user.getLastName())
            || user.getLastName().isBlank()
            || !validateNames.isValidName(user.getLastName())) {
          logger.warn(Fields.invalid + Fields.lastName + Fields.for_ + user);
          count++;
          i++;
          returnMessage = returnMessage + ExceptionMessages.invalidLastName;
        }
      case 3:
        if (isEmpty(user.getContactNo())
            || user.getContactNo().isBlank()
            || !validateContactNo.isValidContact(user.getContactNo())) {
          logger.warn(Fields.invalid + Fields.contactNumber + Fields.for_ + user);
          count++;
          i++;
          returnMessage = returnMessage + ExceptionMessages.invalidContactNo;
        }
      case 4:
        if (isEmpty(user.getEmailId())
            || user.getEmailId().isBlank()
            || !validateEmail.isValidEmail(user.getEmailId())) {
          logger.warn(Fields.invalid + Fields.emailId + Fields.for_ + user);
          count++;
          i++;
          returnMessage = returnMessage + ExceptionMessages.invalidEmailId;
        }
      case 5:
        if (isEmpty(user.getPassword())
            || user.getPassword().isBlank()
            || !validatePassword.isValidPassword(user.getPassword())) {
          logger.warn(Fields.invalid + Fields.password + Fields.for_ + user);
          count++;
          i++;
          returnMessage = returnMessage + ExceptionMessages.invalidPassword;
        }
      case 6:
        if (isEmpty(user.getConfirmPassword())
            || user.getConfirmPassword().isBlank()
            || !validatePassword.isValidPassword(user.getConfirmPassword())) {
          logger.warn(Fields.invalid + Fields.confirmPassword + Fields.for_ + user);
          count++;
          i++;
          returnMessage = returnMessage + ExceptionMessages.invalidConfirmPassword;
        }
      case 7:
        if (count == 0) {
          logger.info(Fields.validMessage + user);
          returnMessage = Fields.valid;
        }
      default:
        return returnMessage;
    }
  }

  public String isDuplicate(User user) { //isUserAlreadyExist
    List<User> saveUser = userRepository.findForSave(user.getEmailId(), user.getContactNo());
    if (!saveUser.isEmpty()) {
      logger.warn(Fields.duplicate + user);
      return Fields.notEmpty;
    } else {
      logger.info(Fields.notDuplicate + user);
      return Fields.empty;
    }
  }
}
