package dev.mauriciodev.progressor.application.nutritionist;

import dev.mauriciodev.progressor.domain.nutritionist.Nutritionist;

public final class NutritionistMapper {

  private NutritionistMapper() {
  }

  public static NutritionistResponse toResponse(Nutritionist nutritionist) {
    return new NutritionistResponse(nutritionist.getId(), nutritionist.getName(),
        nutritionist.getEmail(), nutritionist.getPhone(), nutritionist.getCrn(),
        nutritionist.getSpecialty());
  }
}
