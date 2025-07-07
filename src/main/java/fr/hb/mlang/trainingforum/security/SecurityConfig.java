package fr.hb.mlang.trainingforum.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain accessControl(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(authorize -> authorize
            // We set specific routes to require authentication
            .requestMatchers("/new-post").authenticated()
            // We can set specific CRUD operations to required authentication
            .requestMatchers(HttpMethod.POST, "/post/**").authenticated()
            // The rest of our routes are public
            .anyRequest().permitAll()
        )
        // Replace the default Spring Security login page with our own
        .formLogin(formLogin -> formLogin
            .loginPage("/login").permitAll()
            .defaultSuccessUrl("/", true)
        )

        .build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
