package fr.hb.mlang.trainingforum.security.controller;

import fr.hb.mlang.trainingforum.entity.User;
import fr.hb.mlang.trainingforum.enums.Role;
import fr.hb.mlang.trainingforum.repository.UserRepository;
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
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String displayRegister(Model model) {
        model.addAttribute("user", new UserRegisterDTO());

        return "register";
    }

    @PostMapping
    public String processRegister(@ModelAttribute("user") @Valid UserRegisterDTO userRegisterDTO, BindingResult bindingResult, Model model) {

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
//            model.addAttribute("user", new UserRegisterDTO(userRegisterDTO.getUsername(), null, null));
            return "register";
        }

        // Otherwise, hash password, set default role & persist new user
        String hashedPassword = passwordEncoder.encode(userRegisterDTO.getPassword());

        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(hashedPassword);
        user.setRole(Role.USER);

        // Persist user
        userRepository.save(user);

        return "redirect:/login";
    }
}
