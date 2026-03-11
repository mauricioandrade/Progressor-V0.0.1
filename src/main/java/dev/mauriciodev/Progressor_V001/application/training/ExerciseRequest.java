package dev.mauriciodev.Progressor_V001.application.training;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ExerciseRequest(@NotBlank String name, String videoUrl,
                              @NotNull @Positive Integer sets, @NotBlank String repetitions,
                              String notes) {

}