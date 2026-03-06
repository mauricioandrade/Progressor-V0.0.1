package dev.mauriciodev.Progressor_V001.presentation.auth;

import dev.mauriciodev.Progressor_V001.application.auth.AuthResponse;
import dev.mauriciodev.Progressor_V001.application.auth.AuthService;
import dev.mauriciodev.Progressor_V001.application.auth.LoginRequest;
import dev.mauriciodev.Progressor_V001.application.auth.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public final class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
    return ResponseEntity.ok(authService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }
}
