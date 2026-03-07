package dev.mauriciodev.Progressor_V001.application.training;

import dev.mauriciodev.Progressor_V001.domain.training.TrainingPlan;

public final class TrainingPlanMapper {

  private TrainingPlanMapper() {
  }

  public static TrainingPlanResponse toResponse(TrainingPlan plan) {
    return new TrainingPlanResponse(plan.getId(), plan.getName(), plan.getDurationWeeks(),
        plan.getLevel(), plan.getExercises());
  }
}