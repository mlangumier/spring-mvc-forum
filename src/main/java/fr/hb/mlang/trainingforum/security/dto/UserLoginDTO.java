package fr.hb.mlang.trainingforum.security.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * User DTO for logging into the app
 */
public class UserLoginDTO {

  @NotBlank(message = "Username missing")
  private String username;

  @NotBlank(message = "Password missing")
  private String password;

  /**
   * Empty constructor to prepare the form
   */
  public UserLoginDTO() {
  }

  /**
   * Full constructor with the form's data
   *
   * @param username
   * @param password
   */
  public UserLoginDTO(String username, String password) {
    this.username = username;
    this.password = password;
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
}
