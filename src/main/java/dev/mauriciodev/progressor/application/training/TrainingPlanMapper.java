package dev.mauriciodev.progressor.application.training;

import dev.mauriciodev.progressor.domain.training.TrainingPlan;
import java.util.List;

public final class TrainingPlanMapper {

  private TrainingPlanMapper() {
  }

  public static TrainingPlanResponse toResponse(TrainingPlan plan) {
    List<ExerciseResponse> exercises = plan.getExercises().stream().map(
        e -> new ExerciseResponse(e.getName(), e.getVideoUrl(), e.getSets(), e.getRepetitions(),
            e.getNotes())).toList();

    return new TrainingPlanResponse(plan.getId(), plan.getName(), plan.getDurationWeeks(),
        plan.getLevel(), exercises);
  }
}