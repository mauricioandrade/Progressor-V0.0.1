package dev.mauriciodev.progressor.presentation.trainer;

import dev.mauriciodev.progressor.application.student.StudentMapper;
import dev.mauriciodev.progressor.application.student.StudentResponse;
import dev.mauriciodev.progressor.application.trainer.PersonalTrainerService;
import dev.mauriciodev.progressor.application.trainer.TrainerMapper;
import dev.mauriciodev.progressor.application.trainer.TrainerRequest;
import dev.mauriciodev.progressor.application.trainer.TrainerResponse;
import dev.mauriciodev.progressor.domain.student.Student;
import dev.mauriciodev.progressor.domain.trainer.PersonalTrainer;
import dev.mauriciodev.progressor.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/trainers")
@Tag(name = "Personal Trainers", description = "Endpoints for managing personal trainers")
public class PersonalTrainerController {

  private final PersonalTrainerService personalTrainerService;

  public PersonalTrainerController(PersonalTrainerService personalTrainerService) {
    this.personalTrainerService = personalTrainerService;
  }

  @GetMapping("/me")
  @Operation(summary = "Get own profile", description = "Returns the authenticated trainer's profile")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
      @ApiResponse(responseCode = "404", description = "Trainer not found")})
  public ResponseEntity<TrainerResponse> getMe(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    PersonalTrainer trainer = personalTrainerService.findByUserId(user.getId());
    return ResponseEntity.ok(TrainerMapper.toResponse(trainer));
  }

  @PostMapping(value = "/me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "Upload trainer avatar", description = "Accepts JPEG, PNG or WebP up to 5MB")
  @ApiResponses({@ApiResponse(responseCode = "204", description = "Avatar uploaded successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid file type or size")})
  public ResponseEntity<Void> uploadAvatar(@RequestParam("file") MultipartFile file,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    try {
      personalTrainerService.uploadAvatar(user.getId(), file);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/me/avatar")
  @Operation(summary = "Get trainer avatar", description = "Returns the trainer's avatar image bytes")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Avatar returned"),
      @ApiResponse(responseCode = "404", description = "No avatar found")})
  public ResponseEntity<byte[]> getAvatar(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    PersonalTrainer trainer = personalTrainerService.findByUserId(user.getId());
    if (trainer.getAvatarData() == null) {
      return ResponseEntity.notFound().build();
    }
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.parseMediaType(trainer.getAvatarContentType()));
    headers.setContentLength(trainer.getAvatarData().length);
    return new ResponseEntity<>(trainer.getAvatarData(), headers, HttpStatus.OK);
  }

  @GetMapping("/me/students")
  @Operation(summary = "List supervised students", description = "Returns all students linked to the authenticated trainer")
  @ApiResponse(responseCode = "200", description = "Students listed successfully")
  public ResponseEntity<List<StudentResponse>> getMyStudents(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    List<StudentResponse> students = personalTrainerService.findMyStudents(user.getId()).stream()
        .map(StudentMapper::toResponse).toList();
    return ResponseEntity.ok(students);
  }

  @PostMapping("/me/students/{studentId}")
  @Operation(summary = "Link student to trainer", description = "Assigns a student to the authenticated trainer")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Student linked successfully"),
      @ApiResponse(responseCode = "404", description = "Student or trainer not found")})
  public ResponseEntity<StudentResponse> linkStudent(@PathVariable Long studentId,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Student student = personalTrainerService.linkStudent(user.getId(), studentId);
    return ResponseEntity.ok(StudentMapper.toResponse(student));
  }

  @DeleteMapping("/me/students/{studentId}")
  @Operation(summary = "Unlink student from trainer")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Student unlinked successfully"),
      @ApiResponse(responseCode = "404", description = "Student or trainer not found")})
  public ResponseEntity<StudentResponse> unlinkStudent(@PathVariable Long studentId,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Student student = personalTrainerService.unlinkStudent(user.getId(), studentId);
    return ResponseEntity.ok(StudentMapper.toResponse(student));
  }

  @PostMapping
  @Operation(summary = "Register a personal trainer")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Trainer registered successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid request data")})
  public ResponseEntity<TrainerResponse> register(@Valid @RequestBody TrainerRequest request) {
    PersonalTrainer trainer = new PersonalTrainer(null, request.name(), request.email(),
        request.phone(), request.cref(), request.specialty());
    PersonalTrainer saved = personalTrainerService.register(trainer);
    return ResponseEntity.status(HttpStatus.CREATED).body(TrainerMapper.toResponse(saved));
  }

  @GetMapping
  @Operation(summary = "List all trainers")
  @ApiResponse(responseCode = "200", description = "Trainers listed successfully")
  public ResponseEntity<List<TrainerResponse>> findAll() {
    List<TrainerResponse> response = personalTrainerService.findAll().stream()
        .map(TrainerMapper::toResponse).toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Find trainer by ID")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Trainer found"),
      @ApiResponse(responseCode = "404", description = "Trainer not found")})
  public ResponseEntity<TrainerResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(TrainerMapper.toResponse(personalTrainerService.findById(id)));
  }
}