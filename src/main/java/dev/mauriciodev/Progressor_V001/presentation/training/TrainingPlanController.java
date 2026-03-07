package dev.mauriciodev.Progressor_V001.presentation.training;

import dev.mauriciodev.Progressor_V001.domain.student.Student;
import dev.mauriciodev.Progressor_V001.domain.training.TrainingPlan;
import dev.mauriciodev.Progressor_V001.application.training.TrainingPlanRequest;
import dev.mauriciodev.Progressor_V001.application.student.StudentResponse;
import dev.mauriciodev.Progressor_V001.application.training.TrainingPlanResponse;
import dev.mauriciodev.Progressor_V001.application.training.TrainingPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  @Operation(summary = "Create a training plan", description = "Creates and saves a new training plan")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Training plan created successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid request data")
  })
  public ResponseEntity<TrainingPlanResponse> create(@Valid @RequestBody TrainingPlanRequest request) {
    TrainingPlan plan = new TrainingPlan(
        null,
        request.name(),
        request.durationWeeks(),
        request.level(),
        request.exercises()
    );
    TrainingPlan saved = trainingPlanService.create(plan);
    return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
  }

  @GetMapping
  @Operation(summary = "List all training plans", description = "Returns a list of all registered training plans")
  @ApiResponse(responseCode = "200", description = "Training plans listed successfully")
  public ResponseEntity<List<TrainingPlanResponse>> findAll() {
    List<TrainingPlanResponse> response = trainingPlanService.findAll()
        .stream()
        .map(this::toResponse)
        .toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Find training plan by ID", description = "Returns a single training plan by its ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Training plan found"),
      @ApiResponse(responseCode = "404", description = "Training plan not found")
  })
  public ResponseEntity<TrainingPlanResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(toResponse(trainingPlanService.findById(id)));
  }

  @PostMapping("/{id}/assign/{studentId}")
  @Operation(summary = "Assign plan to student",
      description = "Assigns a training plan to a specific student")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Plan assigned successfully"),
      @ApiResponse(responseCode = "404", description = "Training plan or student not found")
  })
  public ResponseEntity<StudentResponse> assignToStudent(
      @PathVariable Long id,
      @PathVariable Long studentId) {
    Student student = trainingPlanService.assignToStudent(id, studentId);
    return ResponseEntity.ok(new StudentResponse(
        student.getId(),
        student.getName(),
        student.getEmail(),
        student.getPhone(),
        student.getAge(),
        student.getWeight(),
        student.getHeight(),
        student.getGoal(),
        student.getTrainingLevel(),
        student.getCurrentTrainingPlan().getName()
    ));
  }

  private TrainingPlanResponse toResponse(TrainingPlan plan) {
    return new TrainingPlanResponse(
        plan.getId(),
        plan.getName(),
        plan.getDurationWeeks(),
        plan.getLevel(),
        plan.getExercises()
    );
  }
}