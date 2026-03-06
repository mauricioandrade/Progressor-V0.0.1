package dev.mauriciodev.Progressor_V001.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @GetMapping("/me")
  public ResponseEntity<String> getCurrentUser(Authentication authentication) {
    String userEmail = authentication.getName();
    return ResponseEntity.ok("You are authenticated as: " + userEmail);
  }
}