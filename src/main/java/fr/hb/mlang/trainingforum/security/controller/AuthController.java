package fr.hb.mlang.trainingforum.security.controller;

import fr.hb.mlang.trainingforum.entity.User;
import fr.hb.mlang.trainingforum.enums.Role;
import fr.hb.mlang.trainingforum.repository.UserRepository;
import fr.hb.mlang.trainingforum.security.dto.UserLoginDTO;
import fr.hb.mlang.trainingforum.security.dto.UserRegisterDTO;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AuthController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }


  @GetMapping("/register")
  public String displayRegisterForm(Model model) {
    model.addAttribute("user", new UserRegisterDTO());

    return "register-form";
  }

  @PostMapping("/register")
  public String processRegisterForm(@ModelAttribute("user") @Valid UserRegisterDTO userRegisterDTO,
      BindingResult bindingResult, Model model) {

    // Check password & confirmPassword have the same value
    if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
      bindingResult.addError(new FieldError("user", "password", "Passwords don't match"));
      bindingResult.addError(new FieldError("user", "confirmPassword", "Passwords don't match"));
    }

    // Check if username is available
    Optional<User> foundUser = userRepository.findByUsername(userRegisterDTO.getUsername());
    if (foundUser.isPresent()) {
      userRegisterDTO.setUsername(null);
      bindingResult.addError(new FieldError("user", "username", "Username already exists"));
    }

    // If errors, back to register form & display errors (keep username if it doesn't exist yet, but password error)
    if (bindingResult.hasErrors()) {
      return "register-form";
    }

    // Otherwise, create new User, hash password & set default role
    String hashedPassword = passwordEncoder.encode(userRegisterDTO.getPassword());

    User user = new User();
    user.setUsername(userRegisterDTO.getUsername());
    user.setPassword(hashedPassword);
    user.setRole(Role.USER);

    // Persist user
    userRepository.save(user);

    return "redirect:/login";
  }

  @GetMapping("/login")
  public String displayLoginForm(Model model) {
    model.addAttribute("user", new UserLoginDTO());

    return "login-form";
  }
}
