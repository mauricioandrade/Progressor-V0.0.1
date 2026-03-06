package dev.mauriciodev.Progressor_V001.application.auth;

import dev.mauriciodev.Progressor_V001.domain.user.Role;
import dev.mauriciodev.Progressor_V001.domain.user.User;
import dev.mauriciodev.Progressor_V001.infrastructure.persistence.UserRepository;
import dev.mauriciodev.Progressor_V001.infrastructure.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public final class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthService(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      JwtService jwtService,
      AuthenticationManager authenticationManager
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  public AuthResponse register(RegisterRequest request) {
    if (userRepository.findByEmail(request.email()).isPresent()) {
      throw new IllegalArgumentException("Email is already in use.");
    }

    User user = User.create(
        request.email(),
        passwordEncoder.encode(request.password()),
        Role.USER
    );

    userRepository.save(user);
    String token = jwtService.generateToken(user);

    return new AuthResponse(token);
  }

  public AuthResponse login(LoginRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.email(), request.password())
      );
    } catch (Exception e) {
      throw new RuntimeException("Invalid credentials or user not found.", e);
    }

    User user = userRepository.findByEmail(request.email())
        .orElseThrow(() -> new IllegalStateException("User not found after authentication."));

    String token = jwtService.generateToken(user);

    return new AuthResponse(token);
  }
}