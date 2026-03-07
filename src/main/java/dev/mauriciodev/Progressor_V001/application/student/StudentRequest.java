package dev.mauriciodev.Progressor_V001.application.student;

import dev.mauriciodev.Progressor_V001.domain.shared.Goal;
import dev.mauriciodev.Progressor_V001.domain.shared.TrainingLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StudentRequest(@NotBlank String name, @NotBlank String email, String phone,
                             @NotNull @Positive Integer age, @NotNull @Positive Double weight,
                             @NotNull @Positive Double height, @NotNull Goal goal,
                             @NotNull TrainingLevel trainingLevel) {

}