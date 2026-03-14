package dev.mauriciodev.progressor.application.nutrition;

import dev.mauriciodev.progressor.domain.shared.DietFocus;
import java.util.List;

public record DietPlanResponse(Long id, String name, Integer durationWeeks, DietFocus focus,
                               Integer dailyCalories, List<MealResponse> meals) {

}