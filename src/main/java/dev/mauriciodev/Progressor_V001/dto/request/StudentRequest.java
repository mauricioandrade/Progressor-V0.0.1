package dev.mauriciodev.Progressor_V001.dto.request;

import dev.mauriciodev.Progressor_V001.domain.enums.Goal;
import dev.mauriciodev.Progressor_V001.domain.enums.TrainingLevel;

public record StudentRequest(
    String name,
    String email,
    String phone,
    Integer age,
    Double weight,
    Double height,
    Goal goal,
    TrainingLevel trainingLevel
) {

}