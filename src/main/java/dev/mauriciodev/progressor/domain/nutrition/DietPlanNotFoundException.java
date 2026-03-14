package dev.mauriciodev.progressor.domain.nutrition;

public class DietPlanNotFoundException extends RuntimeException {

  public DietPlanNotFoundException(Long id) {
    super("Diet plan not found with id: " + id);
  }
}
