package dev.mauriciodev.progressor.domain.nutritionist;

import java.util.UUID;

public class NutritionistNotFoundException extends RuntimeException {

  public NutritionistNotFoundException(Long id) {
    super("Nutritionist not found with id: " + id);
  }

  public NutritionistNotFoundException(UUID userId) {
    super("Nutritionist not found for user: " + userId);
  }
}