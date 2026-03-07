package dev.mauriciodev.Progressor_V001.presentation;

import dev.mauriciodev.Progressor_V001.application.auth.UserProfileResponse;
import dev.mauriciodev.Progressor_V001.domain.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @GetMapping("/me")
  public ResponseEntity<UserProfileResponse> getCurrentUser(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    return ResponseEntity.ok(
        new UserProfileResponse(user.getId(), user.getEmail(), user.getRole()));
  }
}