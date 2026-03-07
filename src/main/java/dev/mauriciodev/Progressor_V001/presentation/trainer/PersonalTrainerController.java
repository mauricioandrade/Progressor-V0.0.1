package dev.mauriciodev.Progressor_V001.presentation.trainer;

import dev.mauriciodev.Progressor_V001.domain.trainer.PersonalTrainer;
import dev.mauriciodev.Progressor_V001.application.auth.trainer.TrainerRequest;
import dev.mauriciodev.Progressor_V001.application.auth.trainer.TrainerResponse;
import dev.mauriciodev.Progressor_V001.application.auth.trainer.PersonalTrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/trainers")
@Tag(name = "Personal Trainers", description = "Endpoints for managing personal trainers")
public class PersonalTrainerController {

  private final PersonalTrainerService personalTrainerService;

  public PersonalTrainerController(PersonalTrainerService personalTrainerService) {
    this.personalTrainerService = personalTrainerService;
  }

  @PostMapping
  @Operation(summary = "Register a personal trainer",
      description = "Creates and saves a new personal trainer in the system")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Trainer registered successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid request data")
  })
  public ResponseEntity<TrainerResponse> register(@RequestBody TrainerRequest request) {
    PersonalTrainer trainer = new PersonalTrainer(
        null,
        request.name(),
        request.email(),
        request.phone(),
        request.cref(),
        request.specialty()
    );
    PersonalTrainer saved = personalTrainerService.register(trainer);
    return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
  }

  @GetMapping
  @Operation(summary = "List all trainers", description = "Returns a list of all registered personal trainers")
  @ApiResponse(responseCode = "200", description = "Trainers listed successfully")
  public ResponseEntity<List<TrainerResponse>> findAll() {
    List<TrainerResponse> response = personalTrainerService.findAll()
        .stream()
        .map(this::toResponse)
        .toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Find trainer by ID", description = "Returns a single personal trainer by their ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Trainer found"),
      @ApiResponse(responseCode = "404", description = "Trainer not found")
  })
  public ResponseEntity<TrainerResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(toResponse(personalTrainerService.findById(id)));
  }

  private TrainerResponse toResponse(PersonalTrainer trainer) {
    return new TrainerResponse(
        trainer.getId(),
        trainer.getName(),
        trainer.getEmail(),
        trainer.getPhone(),
        trainer.getCref(),
        trainer.getSpecialty()
    );
  }
}