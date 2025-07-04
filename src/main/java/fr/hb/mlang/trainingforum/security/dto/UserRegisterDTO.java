package fr.hb.mlang.trainingforum.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * User DTO for registration on the app
 */
public class UserRegisterDTO {

  @NotBlank(message = "Username can't be empty")
  @Size(min = 2, max = 150, message = "Your username must contain between 2 and 150 characters")
  private String username;

  @NotBlank(message = "Password can't be empty")
  @Size(min = 4, max = 64, message = "Your password must contains between 4 and 64 characters")
  private String password;

  // We don't need any other annotation since we're going to compare `confirmPassword` with `password`
  @NotBlank(message = "Please, confirm your password")
  private String confirmPassword;

  /**
   * Empty constructor to prepare the form
   */
  public UserRegisterDTO() {
  }

  /**
   * Full constructor with the form's data
   *
   * @param username        unique username the user will use to log in
   * @param password        string the user will use to log in
   * @param confirmPassword
   */
  public UserRegisterDTO(String username, String password, String confirmPassword) {
    this.username = username;
    this.password = password;
    this.confirmPassword = confirmPassword;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }


}
