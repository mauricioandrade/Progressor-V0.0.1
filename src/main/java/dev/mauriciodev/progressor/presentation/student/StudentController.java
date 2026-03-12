package dev.mauriciodev.progressor.presentation.student;

import dev.mauriciodev.progressor.application.student.StudentMapper;
import dev.mauriciodev.progressor.application.student.StudentRequest;
import dev.mauriciodev.progressor.application.student.StudentResponse;
import dev.mauriciodev.progressor.application.student.StudentService;
import dev.mauriciodev.progressor.application.student.StudentUpdateRequest;
import dev.mauriciodev.progressor.application.training.TrainingPlanMapper;
import dev.mauriciodev.progressor.application.training.TrainingPlanResponse;
import dev.mauriciodev.progressor.domain.student.Student;
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
  public ResponseEntity<StudentResponse> register(@Valid @RequestBody StudentRequest request) {
    Student student = StudentMapper.toEntity(request);
    Student saved = studentService.register(student);
    return ResponseEntity.status(HttpStatus.CREATED).body(StudentMapper.toResponse(saved));
  }

  @GetMapping
  @Operation(summary = "List all students")
  public ResponseEntity<List<StudentResponse>> findAll() {
    List<StudentResponse> response = studentService.findAll().stream()
        .map(StudentMapper::toResponse).toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Find student by ID")
  public ResponseEntity<StudentResponse> findById(@PathVariable Long id) {
    Student student = studentService.findById(id);
    return ResponseEntity.ok(StudentMapper.toResponse(student));
  }

  @PatchMapping("/{id}/progress")
  @Operation(summary = "Progress student level and assign new plan")
  public ResponseEntity<StudentResponse> progress(@PathVariable Long id) {
    Student student = studentService.progress(id);
    return ResponseEntity.ok(StudentMapper.toResponse(student));
  }

  @GetMapping("/me")
  @Operation(summary = "Get own profile")
  public ResponseEntity<StudentResponse> getMe(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Student student = studentService.findByUserId(user.getId());
    return ResponseEntity.ok(StudentMapper.toResponse(student));
  }

  @PutMapping("/me")
  @Operation(summary = "Update own profile")
  public ResponseEntity<StudentResponse> updateMe(@Valid @RequestBody StudentUpdateRequest request,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Student updated = studentService.update(user.getId(), request);
    return ResponseEntity.ok(StudentMapper.toResponse(updated));
  }

  @PatchMapping("/me/progress")
  @Operation(summary = "Evolve own student level")
  public ResponseEntity<StudentResponse> evolveProgress(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Student updated = studentService.evolveProgress(user.getId());
    return ResponseEntity.ok(StudentMapper.toResponse(updated));
  }

  @PostMapping(value = "/me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "Upload profile avatar")
  public ResponseEntity<Void> uploadAvatar(@RequestParam("file") MultipartFile file,
      Authentication authentication) throws IOException {
    User user = (User) authentication.getPrincipal();
    studentService.uploadAvatar(user.getId(), file);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/me/avatar")
  @Operation(summary = "Get profile avatar")
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