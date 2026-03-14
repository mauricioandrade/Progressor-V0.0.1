package dev.mauriciodev.progressor.application.nutrition;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record MealRequest(@NotBlank String name, String description, @Positive Integer calories,
                          String time) {

}