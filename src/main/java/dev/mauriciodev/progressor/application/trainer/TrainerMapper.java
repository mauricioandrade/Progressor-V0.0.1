package dev.mauriciodev.progressor.application.trainer;

import dev.mauriciodev.progressor.domain.trainer.PersonalTrainer;

public final class TrainerMapper {

  private TrainerMapper() {
  }

  public static TrainerResponse toResponse(PersonalTrainer trainer) {
    return new TrainerResponse(trainer.getId(), trainer.getName(), trainer.getEmail(),
        trainer.getPhone(), trainer.getCref(), trainer.getSpecialty());
  }
}