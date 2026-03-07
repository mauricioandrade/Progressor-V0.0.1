package dev.mauriciodev.Progressor_V001.presentation.student;

import dev.mauriciodev.Progressor_V001.application.student.StudentMapper;
import dev.mauriciodev.Progressor_V001.application.student.StudentRequest;
import dev.mauriciodev.Progressor_V001.application.student.StudentResponse;
import dev.mauriciodev.Progressor_V001.application.student.StudentService;
import dev.mauriciodev.Progressor_V001.application.student.StudentUpdateRequest;
import dev.mauriciodev.Progressor_V001.application.training.TrainingPlanMapper;
import dev.mauriciodev.Progressor_V001.application.training.TrainingPlanResponse;
import dev.mauriciodev.Progressor_V001.domain.student.Student;
import dev.mauriciodev.Progressor_V001.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/students")
@Tag(name = "Students", description = "Endpoints for managing gym students")
public class StudentController {

  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @PostMapping
  @Operation(summary = "Register a new student", description = "Creates and saves a new student in the system")
  @ApiResponses({@ApiResponse(responseCode = "201", description = "Student created successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid request data")})
  public ResponseEntity<StudentResponse> register(@Valid @RequestBody StudentRequest request) {
    Student student = new Student(null, request.name(), request.email(), request.phone(),
        request.age(), request.weight(), request.height(), request.goal(), request.trainingLevel());
    Student saved = studentService.register(student);
    return ResponseEntity.status(HttpStatus.CREATED).body(StudentMapper.toResponse(saved));
  }

  @GetMapping
  @Operation(summary = "List all students", description = "Returns a list of all registered students")
  @ApiResponse(responseCode = "200", description = "Students listed successfully")
  public ResponseEntity<List<StudentResponse>> findAll() {
    List<StudentResponse> response = studentService.findAll().stream()
        .map(StudentMapper::toResponse).toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Find student by ID", description = "Returns a single student by their ID")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Student found"),
      @ApiResponse(responseCode = "404", description = "Student not found")})
  public ResponseEntity<StudentResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(StudentMapper.toResponse(studentService.findById(id)));
  }

  @PatchMapping("/{id}/progress")
  @Operation(summary = "Progress student level", description = "Evolves the student from their current training level to the next one")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Student progressed successfully"),
      @ApiResponse(responseCode = "400", description = "Student is already at the maximum level"),
      @ApiResponse(responseCode = "404", description = "Student not found")})
  public ResponseEntity<StudentResponse> progress(@PathVariable Long id) {
    try {
      Student student = studentService.progress(id);
      return ResponseEntity.ok(StudentMapper.toResponse(student));
    } catch (IllegalStateException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/{id}/history")
  @Operation(summary = "Get training history", description = "Returns all training plans the student has been assigned to")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "History retrieved successfully"),
      @ApiResponse(responseCode = "404", description = "Student not found")})
  public ResponseEntity<List<TrainingPlanResponse>> getHistory(@PathVariable Long id) {
    Student student = studentService.findById(id);
    List<TrainingPlanResponse> history = student.getTrainingHistory().stream()
        .map(TrainingPlanMapper::toResponse).toList();
    return ResponseEntity.ok(history);
  }

  @GetMapping("/me")
  @Operation(summary = "Get own profile", description = "Returns the authenticated student's profile")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
      @ApiResponse(responseCode = "403", description = "Access denied"),
      @ApiResponse(responseCode = "404", description = "Student not found")})
  public ResponseEntity<StudentResponse> getMe(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Student student = studentService.findByUserId(user.getId());
    return ResponseEntity.ok(StudentMapper.toResponse(student));
  }

  @PutMapping("/me")
  @Operation(summary = "Update own profile", description = "Updates the authenticated student's profile")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Profile updated successfully"),
      @ApiResponse(responseCode = "403", description = "Access denied"),
      @ApiResponse(responseCode = "404", description = "Student not found")})
  public ResponseEntity<StudentResponse> updateMe(@Valid @RequestBody StudentUpdateRequest request,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Student updated = studentService.update(user.getId(), request);
    return ResponseEntity.ok(StudentMapper.toResponse(updated));
  }
}