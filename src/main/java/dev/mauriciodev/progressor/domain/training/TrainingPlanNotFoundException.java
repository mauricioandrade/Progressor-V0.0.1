package dev.mauriciodev.progressor.domain.training;

public class TrainingPlanNotFoundException extends RuntimeException {

  public TrainingPlanNotFoundException(Long id) {
    super("Training plan not found with id: " + id);
  }

  public TrainingPlanNotFoundException(String message) {
    super(message);
  }
}