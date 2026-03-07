package dev.mauriciodev.Progressor_V001.application.student;

import dev.mauriciodev.Progressor_V001.domain.shared.Goal;
import jakarta.validation.constraints.Positive;

public record StudentUpdateRequest(String name, String phone, @Positive Integer age,
                                   @Positive Double weight, @Positive Double height, Goal goal) {

}
