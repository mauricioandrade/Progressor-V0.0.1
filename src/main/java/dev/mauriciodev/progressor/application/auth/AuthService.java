package dev.mauriciodev.progressor.application.auth;

import dev.mauriciodev.progressor.domain.nutritionist.Nutritionist;
import dev.mauriciodev.progressor.domain.shared.Goal;
import dev.mauriciodev.progressor.domain.shared.TrainingLevel;
import dev.mauriciodev.progressor.domain.student.Student;
import dev.mauriciodev.progressor.domain.trainer.PersonalTrainer;
import dev.mauriciodev.progressor.domain.user.Role;
import dev.mauriciodev.progressor.domain.user.User;
import dev.mauriciodev.progressor.infrastructure.persistence.NutritionistRepository;
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
  private final NutritionistRepository nutritionistRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthService(UserRepository userRepository, StudentRepository studentRepository,
      PersonalTrainerRepository trainerRepository, NutritionistRepository nutritionistRepository,
      PasswordEncoder passwordEncoder, JwtService jwtService,
      AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.studentRepository = studentRepository;
    this.trainerRepository = trainerRepository;
    this.nutritionistRepository = nutritionistRepository;
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

    if (role == Role.ADMIN) {
      throw new IllegalArgumentException("ADMIN accounts cannot be created via this endpoint.");
    }

    if (role == Role.TRAINER) {
      if (request.cref() == null || request.cref().isBlank()) {
        throw new IllegalArgumentException("CREF is required for Personal Trainers.");
      }
      if (trainerRepository.findByCref(request.cref()).isPresent()) {
        throw new IllegalArgumentException("CREF is already registered.");
      }
    }

    if (role == Role.NUTRITIONIST) {
      if (request.crn() == null || request.crn().isBlank()) {
        throw new IllegalArgumentException("CRN is required for Nutritionists.");
      }
      if (nutritionistRepository.findByCrn(request.crn()).isPresent()) {
        throw new IllegalArgumentException("CRN is already registered.");
      }
    }

    if (role == Role.STUDENT && request.birthDate() == null) {
      throw new IllegalArgumentException("Birth date is required for students.");
    }

    User user = User.create(request.email(), passwordEncoder.encode(request.password()), role);
    userRepository.save(user);

    if (role == Role.STUDENT) {
      Student student = new Student(null, request.name(), request.email(), request.phone(),
          request.birthDate(), 1.0, 1.0, Goal.CONDITIONING, TrainingLevel.BEGINNER);
      student.setUser(user);
      studentRepository.save(student);
    } else if (role == Role.TRAINER) {
      PersonalTrainer trainer = new PersonalTrainer(null, request.name(), request.email(),
          request.phone(), request.cref(), null);
      trainer.setUser(user);
      trainerRepository.save(trainer);
    } else if (role == Role.NUTRITIONIST) {
      Nutritionist nutritionist = new Nutritionist(null, request.name(), request.email(),
          request.phone(), request.crn(), null);
      nutritionist.setUser(user);
      nutritionistRepository.save(nutritionist);
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