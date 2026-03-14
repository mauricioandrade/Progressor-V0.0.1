package dev.mauriciodev.progressor.application.nutrition;

import dev.mauriciodev.progressor.domain.shared.DietFocus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record DietPlanRequest(@NotNull Long studentId, @NotBlank String name,
                              @NotNull @Positive Integer durationWeeks, @NotNull DietFocus focus,
                              @Positive Integer dailyCalories, @NotNull List<MealRequest> meals) {

}