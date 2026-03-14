package dev.mauriciodev.progressor.presentation.nutritionist;

import dev.mauriciodev.progressor.application.nutritionist.NutritionistMapper;
import dev.mauriciodev.progressor.application.nutritionist.NutritionistResponse;
import dev.mauriciodev.progressor.application.nutritionist.NutritionistService;
import dev.mauriciodev.progressor.application.student.StudentMapper;
import dev.mauriciodev.progressor.application.student.StudentResponse;
import dev.mauriciodev.progressor.domain.nutritionist.Nutritionist;
import dev.mauriciodev.progressor.domain.student.Student;
import dev.mauriciodev.progressor.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/nutritionists")
@Tag(name = "Nutritionists", description = "Endpoints for managing nutritionists")
public class NutritionistController {

  private final NutritionistService nutritionistService;

  public NutritionistController(NutritionistService nutritionistService) {
    this.nutritionistService = nutritionistService;
  }

  @GetMapping("/me")
  @PreAuthorize("hasRole('NUTRITIONIST')")
  @Operation(summary = "Get own profile")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Profile retrieved"),
      @ApiResponse(responseCode = "404", description = "Nutritionist not found")})
  public ResponseEntity<NutritionistResponse> getMe(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Nutritionist nutritionist = nutritionistService.findByUserId(user.getId());
    return ResponseEntity.ok(NutritionistMapper.toResponse(nutritionist));
  }

  @PostMapping(value = "/me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("hasRole('NUTRITIONIST')")
  @Operation(summary = "Upload avatar")
  @ApiResponses({@ApiResponse(responseCode = "204", description = "Avatar uploaded"),
      @ApiResponse(responseCode = "400", description = "Invalid file")})
  public ResponseEntity<Void> uploadAvatar(@RequestParam("file") MultipartFile file,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    try {
      nutritionistService.uploadAvatar(user.getId(), file);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/me/avatar")
  @PreAuthorize("hasRole('NUTRITIONIST')")
  @Operation(summary = "Get avatar")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Avatar returned"),
      @ApiResponse(responseCode = "404", description = "No avatar found")})
  public ResponseEntity<byte[]> getAvatar(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Nutritionist nutritionist = nutritionistService.findByUserId(user.getId());
    if (nutritionist.getAvatarData() == null) {
      return ResponseEntity.notFound().build();
    }
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.parseMediaType(nutritionist.getAvatarContentType()));
    headers.setContentLength(nutritionist.getAvatarData().length);
    return new ResponseEntity<>(nutritionist.getAvatarData(), headers, HttpStatus.OK);
  }

  @GetMapping("/me/students")
  @PreAuthorize("hasRole('NUTRITIONIST')")
  @Operation(summary = "List supervised students")
  @ApiResponse(responseCode = "200", description = "Students listed")
  public ResponseEntity<List<StudentResponse>> getMyStudents(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    List<StudentResponse> students = nutritionistService.findMyStudents(user.getId()).stream()
        .map(StudentMapper::toResponse).toList();
    return ResponseEntity.ok(students);
  }

  @PostMapping("/me/students/{studentId}")
  @PreAuthorize("hasRole('NUTRITIONIST')")
  @Operation(summary = "Link student to nutritionist")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Student linked"),
      @ApiResponse(responseCode = "404", description = "Student or nutritionist not found")})
  public ResponseEntity<StudentResponse> linkStudent(@PathVariable Long studentId,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Student student = nutritionistService.linkStudent(user.getId(), studentId);
    return ResponseEntity.ok(StudentMapper.toResponse(student));
  }

  @DeleteMapping("/me/students/{studentId}")
  @PreAuthorize("hasRole('NUTRITIONIST')")
  @Operation(summary = "Unlink student from nutritionist")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Student unlinked"),
      @ApiResponse(responseCode = "404", description = "Student or nutritionist not found")})
  public ResponseEntity<StudentResponse> unlinkStudent(@PathVariable Long studentId,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Student student = nutritionistService.unlinkStudent(user.getId(), studentId);
    return ResponseEntity.ok(StudentMapper.toResponse(student));
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('NUTRITIONIST', 'ADMIN')")
  @Operation(summary = "List all nutritionists")
  @ApiResponse(responseCode = "200", description = "Nutritionists listed")
  public ResponseEntity<List<NutritionistResponse>> findAll() {
    List<NutritionistResponse> response = nutritionistService.findAll().stream()
        .map(NutritionistMapper::toResponse).toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('NUTRITIONIST', 'ADMIN')")
  @Operation(summary = "Find nutritionist by ID")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Nutritionist found"),
      @ApiResponse(responseCode = "404", description = "Nutritionist not found")})
  public ResponseEntity<NutritionistResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(NutritionistMapper.toResponse(nutritionistService.findById(id)));
  }
}