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
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/students")
@Tag(name = "Students", description = "Endpoints for managing gym students")
public class StudentController {

  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @PostMapping
  @Operation(summary = "Register a new student")
  @ApiResponses({@ApiResponse(responseCode = "201", description = "Student created successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid request data")})
  public ResponseEntity<StudentResponse> register(@Valid @RequestBody StudentRequest request) {
    Student student = new Student(null, request.name(), request.email(), request.phone(),
        request.age(), request.weight(), request.height(), request.goal(), request.trainingLevel());
    Student saved = studentService.register(student);
    return ResponseEntity.status(HttpStatus.CREATED).body(StudentMapper.toResponse(saved));
  }

  @GetMapping
  @Operation(summary = "List all students")
  @ApiResponse(responseCode = "200", description = "Students listed successfully")
  public ResponseEntity<List<StudentResponse>> findAll() {
    List<StudentResponse> response = studentService.findAll().stream()
        .map(StudentMapper::toResponse).toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Find student by ID")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Student found"),
      @ApiResponse(responseCode = "404", description = "Student not found")})
  public ResponseEntity<StudentResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(StudentMapper.toResponse(studentService.findById(id)));
  }

  @PatchMapping("/{id}/progress")
  @Operation(summary = "Progress student level")
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
  @Operation(summary = "Get training history")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "History retrieved successfully"),
      @ApiResponse(responseCode = "404", description = "Student not found")})
  public ResponseEntity<List<TrainingPlanResponse>> getHistory(@PathVariable Long id) {
    Student student = studentService.findById(id);
    List<TrainingPlanResponse> history = student.getTrainingHistory().stream()
        .map(TrainingPlanMapper::toResponse).toList();
    return ResponseEntity.ok(history);
  }

  @GetMapping("/me")
  @Operation(summary = "Get own profile")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
      @ApiResponse(responseCode = "404", description = "Student not found")})
  public ResponseEntity<StudentResponse> getMe(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Student student = studentService.findByUserId(user.getId());
    return ResponseEntity.ok(StudentMapper.toResponse(student));
  }

  @PutMapping("/me")
  @Operation(summary = "Update own profile")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Profile updated successfully"),
      @ApiResponse(responseCode = "404", description = "Student not found")})
  public ResponseEntity<StudentResponse> updateMe(@Valid @RequestBody StudentUpdateRequest request,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Student updated = studentService.update(user.getId(), request);
    return ResponseEntity.ok(StudentMapper.toResponse(updated));
  }

  @PatchMapping("/me/progress")
  @Operation(summary = "Evolve student level")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Level evolved successfully"),
      @ApiResponse(responseCode = "400", description = "Student is already at the highest level"),
      @ApiResponse(responseCode = "404", description = "Student not found")})
  public ResponseEntity<StudentResponse> evolveProgress(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Student updated = studentService.evolveProgress(user.getId());
    return ResponseEntity.ok(StudentMapper.toResponse(updated));
  }

  @PostMapping(value = "/me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "Upload profile avatar", description = "Accepts JPEG, PNG or WebP up to 5MB")
  @ApiResponses({@ApiResponse(responseCode = "204", description = "Avatar uploaded successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid file type or size")})
  public ResponseEntity<Void> uploadAvatar(@RequestParam("file") MultipartFile file,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    try {
      studentService.uploadAvatar(user.getId(), file);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/me/avatar")
  @Operation(summary = "Get profile avatar", description = "Returns the avatar image bytes")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Avatar returned"),
      @ApiResponse(responseCode = "404", description = "No avatar found")})
  public ResponseEntity<byte[]> getAvatar(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Student student = studentService.findAvatarByUserId(user.getId());
    if (student.getAvatarData() == null) {
      return ResponseEntity.notFound().build();
    }
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.parseMediaType(student.getAvatarContentType()));
    headers.setContentLength(student.getAvatarData().length);
    return new ResponseEntity<>(student.getAvatarData(), headers, HttpStatus.OK);
  }
}
