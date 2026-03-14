package dev.mauriciodev.progressor.application.training;

import dev.mauriciodev.progressor.domain.shared.TrainingLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record TrainingPlanUpdateRequest(@NotBlank String name,
                                        @NotNull @Positive Integer durationWeeks,
                                        @NotNull TrainingLevel level,
                                        @NotNull List<ExerciseRequest> exercises) {

}