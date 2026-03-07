package dev.mauriciodev.Progressor_V001.domain.trainer;

public class TrainerNotFoundException extends RuntimeException {

  public TrainerNotFoundException(Long id) {
    super("Trainer not found with id: " + id);
  }
}