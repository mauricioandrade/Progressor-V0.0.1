package dev.mauriciodev.Progressor_V001.domain.trainer;

import java.util.UUID;

public class TrainerNotFoundException extends RuntimeException {

  public TrainerNotFoundException(Long id) {
    super("Trainer not found with id: " + id);
  }

  public TrainerNotFoundException(UUID userId) {
    super("Trainer not found for user: " + userId);
  }
}