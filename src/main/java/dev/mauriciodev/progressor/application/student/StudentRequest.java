package dev.mauriciodev.progressor.application.student;

import dev.mauriciodev.progressor.domain.shared.Goal;
import dev.mauriciodev.progressor.domain.shared.TrainingLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StudentRequest(@NotBlank String name, @NotBlank String email, String phone,
                             @NotNull @Positive Integer age, @NotNull @Positive Double weight,
                             @NotNull @Positive Double height, @NotNull Goal goal,
                             @NotNull TrainingLevel trainingLevel) {

}