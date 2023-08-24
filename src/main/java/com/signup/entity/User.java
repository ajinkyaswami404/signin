package com.signup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_register")
public class User {
  @Getter
  @Id
  @SequenceGenerator(
      name = "user_id_gen",
      sequenceName = "user_id_gen",
      initialValue = 1,
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uid_gen")
  private int id;

  @NotBlank
  @NotEmpty
  @Column(name = "first_name", length = 20)
  private String firstName;

  @NotBlank
  @NotEmpty
  @Column(name = "last_name", length = 20)
  private String lastName;

  @NotNull
  @Column(name = "contact", unique = true)
  private String contactNo;

  @NotBlank
  @NotEmpty
  @Column(unique = true)
  private String emailId;

  @NotBlank
  @NotEmpty
  @Column
  private String password;

  @NotBlank(groups = User.class)
  @NotEmpty(groups = User.class)
  @Transient
  private String confirmPassword;
}
