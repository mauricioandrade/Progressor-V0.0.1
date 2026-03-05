package dev.mauriciodev.Progressor_V001.controller;

import dev.mauriciodev.Progressor_V001.domain.entity.PersonalTrainer;
import dev.mauriciodev.Progressor_V001.dto.request.TrainerRequest;
import dev.mauriciodev.Progressor_V001.dto.response.TrainerResponse;
import dev.mauriciodev.Progressor_V001.service.PersonalTrainerService;
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
public class PersonalTrainerController {

  private final PersonalTrainerService personalTrainerService;

  public PersonalTrainerController(PersonalTrainerService personalTrainerService) {
    this.personalTrainerService = personalTrainerService;
  }

  @PostMapping
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
  public ResponseEntity<List<TrainerResponse>> findAll() {
    List<TrainerResponse> response = personalTrainerService.findAll()
        .stream()
        .map(this::toResponse)
        .toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
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