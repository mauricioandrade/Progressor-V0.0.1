package dev.mauriciodev.Progressor_V001.application.auth.training;

import dev.mauriciodev.Progressor_V001.domain.shared.TrainingLevel;
import java.util.List;

public record TrainingPlanResponse(
    Long id,
    String name,
    Integer durationWeeks,
    TrainingLevel level,
    List<String> exercises
) {

}