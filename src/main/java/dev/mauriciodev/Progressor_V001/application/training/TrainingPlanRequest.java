package dev.mauriciodev.Progressor_V001.application.training;

import dev.mauriciodev.Progressor_V001.domain.shared.TrainingLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record TrainingPlanRequest(@NotBlank String name, @NotNull @Positive Integer durationWeeks,
                                  @NotNull TrainingLevel level, @NotNull List<String> exercises) {

}