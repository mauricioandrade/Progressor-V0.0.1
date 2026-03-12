package dev.mauriciodev.progressor.application.student;

import dev.mauriciodev.progressor.domain.shared.Goal;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record StudentUpdateRequest(String name, String phone, LocalDate birthDate,
                                   @Positive Double weight, @Positive Double height, Goal goal) {

}