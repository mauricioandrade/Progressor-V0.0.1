package dev.mauriciodev.progressor.presentation.training;

import dev.mauriciodev.progressor.application.student.StudentMapper;
import dev.mauriciodev.progressor.application.student.StudentResponse;
import dev.mauriciodev.progressor.application.training.TrainingPlanMapper;
import dev.mauriciodev.progressor.application.training.TrainingPlanRequest;
import dev.mauriciodev.progressor.application.training.TrainingPlanResponse;
import dev.mauriciodev.progressor.application.training.TrainingPlanService;
import dev.mauriciodev.progressor.application.training.TrainingPlanUpdateRequest;
import dev.mauriciodev.progressor.domain.student.Student;
import dev.mauriciodev.progressor.domain.training.TrainingPlan;
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
@RequestMapping("/training-plans")
@Tag(name = "Training Plans", description = "Endpoints for managing training plans")
public class TrainingPlanController {

  private final TrainingPlanService trainingPlanService;

  public TrainingPlanController(TrainingPlanService trainingPlanService) {
    this.trainingPlanService = trainingPlanService;
  }

  @PostMapping
  @PreAuthorize("hasRole('TRAINER')")
  @Operation(summary = "Create a training plan for a student")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Training plan created successfully"),
      @ApiResponse(responseCode = "403", description = "Access denied")})
  public ResponseEntity<TrainingPlanResponse> create(
      @Valid @RequestBody TrainingPlanRequest request, Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    TrainingPlan saved = trainingPlanService.createForStudent(user.getId(), request);
    return ResponseEntity.status(HttpStatus.CREATED).body(TrainingPlanMapper.toResponse(saved));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('TRAINER')")
  @Operation(summary = "Update an existing training plan")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Training plan updated successfully"),
      @ApiResponse(responseCode = "403", description = "Access denied"),
      @ApiResponse(responseCode = "404", description = "Training plan not found")})
  public ResponseEntity<TrainingPlanResponse> update(@PathVariable Long id,
      @Valid @RequestBody TrainingPlanUpdateRequest request) {
    TrainingPlan updated = trainingPlanService.update(id, request);
    return ResponseEntity.ok(TrainingPlanMapper.toResponse(updated));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('TRAINER')")
  @Operation(summary = "Delete a training plan")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Training plan deleted successfully"),
      @ApiResponse(responseCode = "403", description = "Access denied"),
      @ApiResponse(responseCode = "404", description = "Training plan not found")})
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    trainingPlanService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/assign/{studentId}")
  @PreAuthorize("hasRole('TRAINER')")
  @Operation(summary = "Assign plan to student")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Plan assigned successfully"),
      @ApiResponse(responseCode = "403", description = "Access denied"),
      @ApiResponse(responseCode = "404", description = "Training plan or student not found")})
  public ResponseEntity<StudentResponse> assignToStudent(@PathVariable Long id,
      @PathVariable Long studentId) {
    Student student = trainingPlanService.assignToStudent(id, studentId);
    return ResponseEntity.ok(StudentMapper.toResponse(student));
  }

  @GetMapping("/me/current")
  @PreAuthorize("hasRole('STUDENT')")
  @Operation(summary = "Get current training plan for the authenticated student")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Training plan found"),
      @ApiResponse(responseCode = "404", description = "No active training plan found")})
  public ResponseEntity<TrainingPlanResponse> getCurrentPlan(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    TrainingPlan plan = trainingPlanService.findCurrentForStudent(user.getId());
    return ResponseEntity.ok(TrainingPlanMapper.toResponse(plan));
  }

  @GetMapping("/me/history")
  @PreAuthorize("hasRole('STUDENT')")
  @Operation(summary = "Get training plan history for the authenticated student")
  @ApiResponse(responseCode = "200", description = "History retrieved successfully")
  public ResponseEntity<List<TrainingPlanResponse>> getPlanHistory(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    List<TrainingPlanResponse> history = trainingPlanService.findHistoryForStudent(user.getId())
        .stream().map(TrainingPlanMapper::toResponse).toList();
    return ResponseEntity.ok(history);
  }

  @GetMapping("/student/{studentId}/current")
  @PreAuthorize("hasAnyRole('TRAINER', 'NUTRITIONIST', 'ADMIN')")
  @Operation(summary = "Get current training plan for a student")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Training plan found"),
      @ApiResponse(responseCode = "403", description = "Access denied"),
      @ApiResponse(responseCode = "404", description = "No active training plan or student not found")})
  public ResponseEntity<TrainingPlanResponse> getCurrentPlanForStudent(
      @PathVariable Long studentId) {
    TrainingPlan plan = trainingPlanService.findCurrentForStudentById(studentId);
    return ResponseEntity.ok(TrainingPlanMapper.toResponse(plan));
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('TRAINER', 'ADMIN')")
  @Operation(summary = "List all training plans")
  @ApiResponse(responseCode = "200", description = "Training plans listed successfully")
  public ResponseEntity<List<TrainingPlanResponse>> findAll() {
    List<TrainingPlanResponse> response = trainingPlanService.findAll().stream()
        .map(TrainingPlanMapper::toResponse).toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('TRAINER', 'ADMIN')")
  @Operation(summary = "Find training plan by ID")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "Training plan found"),
      @ApiResponse(responseCode = "403", description = "Access denied"),
      @ApiResponse(responseCode = "404", description = "Training plan not found")})
  public ResponseEntity<TrainingPlanResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(TrainingPlanMapper.toResponse(trainingPlanService.findById(id)));
  }
}