package dev.mauriciodev.progressor.presentation.student;

import dev.mauriciodev.progressor.application.student.StudentMapper;
import dev.mauriciodev.progressor.application.student.StudentResponse;
import dev.mauriciodev.progressor.application.student.StudentService;
import dev.mauriciodev.progressor.application.student.StudentUpdateRequest;
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
import org.springframework.security.access.prepost.PreAuthorize;
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

  @GetMapping
  @PreAuthorize("hasAnyRole('TRAINER', 'ADMIN')")
  @Operation(summary = "List all students")
  @ApiResponse(responseCode = "200", description = "Students listed successfully")
  public ResponseEntity<List<StudentResponse>> findAll() {
    List<StudentResponse> response = studentService.findAll().stream()
        .map(StudentMapper::toResponse).toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('TRAINER', 'ADMIN')")
  @Operation(summary = "Find student by ID")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Student found"),
      @ApiResponse(responseCode = "403", description = "Access denied"),
      @ApiResponse(responseCode = "404", description = "Student not found")})
  public ResponseEntity<StudentResponse> findById(@PathVariable Long id) {
    Student student = studentService.findById(id);
    return ResponseEntity.ok(StudentMapper.toResponse(student));
  }

  @PatchMapping("/{id}/progress")
  @PreAuthorize("hasAnyRole('TRAINER', 'ADMIN')")
  @Operation(summary = "Progress student level and assign new plan")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Student progressed"),
      @ApiResponse(responseCode = "403", description = "Access denied"),
      @ApiResponse(responseCode = "404", description = "Student not found")})
  public ResponseEntity<StudentResponse> progress(@PathVariable Long id) {
    Student student = studentService.progress(id);
    return ResponseEntity.ok(StudentMapper.toResponse(student));
  }

  @GetMapping("/me")
  @Operation(summary = "Get own profile")
  @ApiResponse(responseCode = "200", description = "Profile retrieved successfully")
  public ResponseEntity<StudentResponse> getMe(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Student student = studentService.findByUserId(user.getId());
    return ResponseEntity.ok(StudentMapper.toResponse(student));
  }

  @PutMapping("/me")
  @Operation(summary = "Update own profile")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Profile updated"),
      @ApiResponse(responseCode = "404", description = "Student not found")})
  public ResponseEntity<StudentResponse> updateMe(@Valid @RequestBody StudentUpdateRequest request,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Student updated = studentService.update(user.getId(), request);
    return ResponseEntity.ok(StudentMapper.toResponse(updated));
  }

  @PatchMapping("/me/progress")
  @Operation(summary = "Evolve own student level")
  @ApiResponse(responseCode = "200", description = "Level evolved")
  public ResponseEntity<StudentResponse> evolveProgress(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Student updated = studentService.evolveProgress(user.getId());
    return ResponseEntity.ok(StudentMapper.toResponse(updated));
  }

  @PostMapping(value = "/me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "Upload profile avatar")
  @ApiResponses({@ApiResponse(responseCode = "204", description = "Avatar uploaded"),
      @ApiResponse(responseCode = "400", description = "Invalid file")})
  public ResponseEntity<Void> uploadAvatar(@RequestParam("file") MultipartFile file,
      Authentication authentication) throws IOException {
    User user = (User) authentication.getPrincipal();
    studentService.uploadAvatar(user.getId(), file);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/me/avatar")
  @Operation(summary = "Get profile avatar")
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