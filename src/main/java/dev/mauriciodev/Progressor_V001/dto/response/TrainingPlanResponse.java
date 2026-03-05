package dev.mauriciodev.Progressor_V001.dto.response;

import dev.mauriciodev.Progressor_V001.domain.enums.TrainingLevel;
import java.util.List;

public record TrainingPlanResponse(
    Long id,
    String name,
    Integer durationWeeks,
    TrainingLevel level,
    List<String> exercises
) {

}