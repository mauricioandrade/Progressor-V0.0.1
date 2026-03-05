package dev.mauriciodev.Progressor_V001.dto.request;

import dev.mauriciodev.Progressor_V001.domain.enums.TrainingLevel;
import java.util.List;

public record TrainingPlanRequest(
    String name,
    Integer durationWeeks,
    TrainingLevel level,
    List<String> exercises
) {

}