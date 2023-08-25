package com.signup.repository;

import com.signup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  @Query(
      "SELECT user FROM User user WHERE(user.firstName =:firstName)"
          + "and (user.lastName =:lastName)"
          + "and (user.emailId =:emailId)"
          + "and (user.contactNo =:contactNo)"
          + "and (user.password =:password)")
  List<User> findUser(
      @Param("firstName") String firstName,
      @Param("lastName") String lastName,
      @Param("emailId") String emailId,
      @Param("contactNo") String contactNo,
      @Param("password") String password);

  @Query(
      "SELECT user FROM User user WHERE(user.emailId =:emailId)"
          + "or (user.contactNo =:contactNo)")
  List<User> findForSave(String emailId, String contactNo); //findUserByEmailAndContact
}
