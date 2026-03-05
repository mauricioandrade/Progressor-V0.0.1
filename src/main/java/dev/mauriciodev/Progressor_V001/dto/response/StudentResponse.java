package dev.mauriciodev.Progressor_V001.dto.response;

import dev.mauriciodev.Progressor_V001.domain.enums.Goal;
import dev.mauriciodev.Progressor_V001.domain.enums.TrainingLevel;

public record StudentResponse(
    Long id,
    String name,
    String email,
    String phone,
    Integer age,
    Double weight,
    Double height,
    Goal goal,
    TrainingLevel trainingLevel,
    String currentTrainingPlan
) {

}
