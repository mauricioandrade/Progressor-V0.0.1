package dev.mauriciodev.progressor.application.training;

import dev.mauriciodev.progressor.domain.shared.TrainingLevel;
import java.util.List;

public record TrainingPlanResponse(Long id, String name, Integer durationWeeks, TrainingLevel level,
                                   List<ExerciseResponse> exercises) {

}