package dev.mauriciodev.Progressor_V001.application.trainer;

import dev.mauriciodev.Progressor_V001.domain.trainer.PersonalTrainer;

public final class TrainerMapper {

  private TrainerMapper() {
  }

  public static TrainerResponse toResponse(PersonalTrainer trainer) {
    return new TrainerResponse(trainer.getId(), trainer.getName(), trainer.getEmail(),
        trainer.getPhone(), trainer.getCref(), trainer.getSpecialty());
  }
}