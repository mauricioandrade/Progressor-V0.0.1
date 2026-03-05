package dev.mauriciodev.Progressor_V001.controller;

import dev.mauriciodev.Progressor_V001.domain.entity.Student;
import dev.mauriciodev.Progressor_V001.domain.entity.TrainingPlan;
import dev.mauriciodev.Progressor_V001.dto.request.TrainingPlanRequest;
import dev.mauriciodev.Progressor_V001.dto.response.StudentResponse;
import dev.mauriciodev.Progressor_V001.dto.response.TrainingPlanResponse;
import dev.mauriciodev.Progressor_V001.service.TrainingPlanService;
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
public class TrainingPlanController {

  private final TrainingPlanService trainingPlanService;

  public TrainingPlanController(TrainingPlanService trainingPlanService) {
    this.trainingPlanService = trainingPlanService;
  }

  @PostMapping
  public ResponseEntity<TrainingPlanResponse> create(@RequestBody TrainingPlanRequest request) {
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
  public ResponseEntity<List<TrainingPlanResponse>> findAll() {
    List<TrainingPlanResponse> response = trainingPlanService.findAll()
        .stream()
        .map(this::toResponse)
        .toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TrainingPlanResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(toResponse(trainingPlanService.findById(id)));
  }

  @PostMapping("/{id}/assign/{studentId}")
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