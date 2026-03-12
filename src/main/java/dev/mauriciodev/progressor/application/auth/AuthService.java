package dev.mauriciodev.progressor.application.auth;

import dev.mauriciodev.progressor.domain.shared.Goal;
import dev.mauriciodev.progressor.domain.shared.TrainingLevel;
import dev.mauriciodev.progressor.domain.student.Student;
import dev.mauriciodev.progressor.domain.trainer.PersonalTrainer;
import dev.mauriciodev.progressor.domain.user.Role;
import dev.mauriciodev.progressor.domain.user.User;
import dev.mauriciodev.progressor.infrastructure.persistence.PersonalTrainerRepository;
import dev.mauriciodev.progressor.infrastructure.persistence.StudentRepository;
import dev.mauriciodev.progressor.infrastructure.persistence.UserRepository;
import dev.mauriciodev.progressor.infrastructure.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final StudentRepository studentRepository;
  private final PersonalTrainerRepository trainerRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthService(UserRepository userRepository, StudentRepository studentRepository,
      PersonalTrainerRepository trainerRepository, PasswordEncoder passwordEncoder,
      JwtService jwtService, AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.studentRepository = studentRepository;
    this.trainerRepository = trainerRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  @Transactional
  public AuthResponse register(RegisterRequest request) {
    if (userRepository.findByEmail(request.email()).isPresent()) {
      throw new IllegalArgumentException("Email is already in use.");
    }

    Role role = request.role();

    if (role == Role.TRAINER) {
      if (request.cref() == null || request.cref().isBlank()) {
        throw new IllegalArgumentException("CREF is required for Personal Trainers.");
      }
      if (trainerRepository.findByCref(request.cref()).isPresent()) {
        throw new IllegalArgumentException("CREF is already registered.");
      }
    }

    User user = User.create(request.email(), passwordEncoder.encode(request.password()), role);
    userRepository.save(user);

    if (role == Role.STUDENT) {
      Student student = new Student(null, request.name(), request.email(), request.phone(), null,
          null, null, Goal.CONDITIONING, TrainingLevel.BEGINNER);
      student.setUser(user);
      studentRepository.save(student);
    } else if (role == Role.TRAINER) {
      PersonalTrainer trainer = new PersonalTrainer(null, request.name(), request.email(),
          request.phone(), request.cref(), null);
      trainer.setUser(user);
      trainerRepository.save(trainer);
    }

    String token = jwtService.generateToken(user);
    return new AuthResponse(token);
  }

  public AuthResponse login(LoginRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.email(), request.password()));
    } catch (BadCredentialsException _) {
      throw new BadCredentialsException("Invalid email or password.");
    } catch (Exception _) {
      throw new RuntimeException("An error occurred during authentication.");
    }

    User user = userRepository.findByEmail(request.email())
        .orElseThrow(() -> new IllegalStateException("User not found after authentication."));

    String token = jwtService.generateToken(user);
    return new AuthResponse(token);
  }
}