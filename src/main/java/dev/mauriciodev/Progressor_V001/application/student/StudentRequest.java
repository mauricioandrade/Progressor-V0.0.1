package dev.mauriciodev.Progressor_V001.application.student;

import dev.mauriciodev.Progressor_V001.domain.shared.Goal;
import dev.mauriciodev.Progressor_V001.domain.shared.TrainingLevel;

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