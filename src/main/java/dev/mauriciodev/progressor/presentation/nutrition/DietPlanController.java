package dev.mauriciodev.progressor.presentation.nutrition;

import dev.mauriciodev.progressor.application.nutrition.DietPlanMapper;
import dev.mauriciodev.progressor.application.nutrition.DietPlanRequest;
import dev.mauriciodev.progressor.application.nutrition.DietPlanResponse;
import dev.mauriciodev.progressor.application.nutrition.DietPlanService;
import dev.mauriciodev.progressor.application.nutrition.DietPlanUpdateRequest;
import dev.mauriciodev.progressor.application.student.StudentMapper;
import dev.mauriciodev.progressor.application.student.StudentResponse;
import dev.mauriciodev.progressor.domain.nutrition.DietPlan;
import dev.mauriciodev.progressor.domain.student.Student;
import dev.mauriciodev.progressor.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diet-plans")
@Tag(name = "Diet Plans", description = "Endpoints for managing diet plans")
public class DietPlanController {

  private final DietPlanService dietPlanService;

  public DietPlanController(DietPlanService dietPlanService) {
    this.dietPlanService = dietPlanService;
  }

  @PostMapping
  @PreAuthorize("hasRole('NUTRITIONIST')")
  @Operation(summary = "Create a diet plan for a student")
  @ApiResponses({@ApiResponse(responseCode = "201", description = "Diet plan created"),
      @ApiResponse(responseCode = "403", description = "Access denied")})
  public ResponseEntity<DietPlanResponse> create(@Valid @RequestBody DietPlanRequest request,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    DietPlan saved = dietPlanService.createForStudent(user.getId(), request);
    return ResponseEntity.status(HttpStatus.CREATED).body(DietPlanMapper.toResponse(saved));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('NUTRITIONIST')")
  @Operation(summary = "Update an existing diet plan")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Diet plan updated"),
      @ApiResponse(responseCode = "403", description = "Access denied"),
      @ApiResponse(responseCode = "404", description = "Diet plan not found")})
  public ResponseEntity<DietPlanResponse> update(@PathVariable Long id,
      @Valid @RequestBody DietPlanUpdateRequest request) {
    DietPlan updated = dietPlanService.update(id, request);
    return ResponseEntity.ok(DietPlanMapper.toResponse(updated));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('NUTRITIONIST')")
  @Operation(summary = "Delete a diet plan")
  @ApiResponses({@ApiResponse(responseCode = "204", description = "Diet plan deleted"),
      @ApiResponse(responseCode = "403", description = "Access denied"),
      @ApiResponse(responseCode = "404", description = "Diet plan not found")})
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    dietPlanService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/assign/{studentId}")
  @PreAuthorize("hasRole('NUTRITIONIST')")
  @Operation(summary = "Assign diet plan to student")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Plan assigned"),
      @ApiResponse(responseCode = "403", description = "Access denied"),
      @ApiResponse(responseCode = "404", description = "Diet plan or student not found")})
  public ResponseEntity<StudentResponse> assignToStudent(@PathVariable Long id,
      @PathVariable Long studentId) {
    Student student = dietPlanService.assignToStudent(id, studentId);
    return ResponseEntity.ok(StudentMapper.toResponse(student));
  }

  @GetMapping("/me/current")
  @PreAuthorize("hasRole('STUDENT')")
  @Operation(summary = "Get current diet plan for the authenticated student")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Diet plan found"),
      @ApiResponse(responseCode = "404", description = "No active diet plan found")})
  public ResponseEntity<DietPlanResponse> getCurrentPlan(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    DietPlan plan = dietPlanService.findCurrentForStudent(user.getId());
    return ResponseEntity.ok(DietPlanMapper.toResponse(plan));
  }

  @GetMapping("/student/{studentId}/current")
  @PreAuthorize("hasAnyRole('NUTRITIONIST', 'TRAINER', 'ADMIN')")
  @Operation(summary = "Get current diet plan for a student")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Diet plan found"),
      @ApiResponse(responseCode = "403", description = "Access denied"),
      @ApiResponse(responseCode = "404", description = "No active diet plan or student not found")})
  public ResponseEntity<DietPlanResponse> getCurrentPlanForStudent(@PathVariable Long studentId) {
    DietPlan plan = dietPlanService.findCurrentForStudentById(studentId);
    return ResponseEntity.ok(DietPlanMapper.toResponse(plan));
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('NUTRITIONIST', 'ADMIN')")
  @Operation(summary = "List all diet plans")
  @ApiResponse(responseCode = "200", description = "Diet plans listed")
  public ResponseEntity<List<DietPlanResponse>> findAll() {
    List<DietPlanResponse> response = dietPlanService.findAll().stream()
        .map(DietPlanMapper::toResponse).toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('NUTRITIONIST', 'ADMIN')")
  @Operation(summary = "Find diet plan by ID")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Diet plan found"),
      @ApiResponse(responseCode = "404", description = "Diet plan not found")})
  public ResponseEntity<DietPlanResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(DietPlanMapper.toResponse(dietPlanService.findById(id)));
  }
}