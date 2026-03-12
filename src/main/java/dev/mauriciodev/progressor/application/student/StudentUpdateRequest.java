package dev.mauriciodev.progressor.application.student;

import dev.mauriciodev.progressor.domain.shared.Goal;
import jakarta.validation.constraints.Positive;

public record StudentUpdateRequest(String name, String phone, @Positive Integer age,
                                   @Positive Double weight, @Positive Double height, Goal goal) {

}
