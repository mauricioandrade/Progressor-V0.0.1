package dev.mauriciodev.Progressor_V001.domain.training;

public class TrainingPlanNotFoundException extends RuntimeException {

  public TrainingPlanNotFoundException(Long id) {
    super("Training plan not found with id: " + id);
  }
}