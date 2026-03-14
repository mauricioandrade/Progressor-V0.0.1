package dev.mauriciodev.progressor.application.nutrition;

import dev.mauriciodev.progressor.domain.nutrition.DietPlan;
import java.util.List;

public final class DietPlanMapper {

  private DietPlanMapper() {
  }

  public static DietPlanResponse toResponse(DietPlan plan) {
    List<MealResponse> meals = plan.getMeals().stream()
        .map(m -> new MealResponse(m.getName(), m.getDescription(), m.getCalories(), m.getTime()))
        .toList();

    return new DietPlanResponse(plan.getId(), plan.getName(), plan.getDurationWeeks(),
        plan.getFocus(), plan.getDailyCalories(), meals);
  }
}
