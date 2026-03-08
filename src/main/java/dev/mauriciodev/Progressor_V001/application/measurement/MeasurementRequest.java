package dev.mauriciodev.Progressor_V001.application.measurement;

import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public record MeasurementRequest(LocalDateTime recordedAt, @Positive Double weightKg,
                                 @Positive Double heightCm, @Positive Double bodyFatPercent,
                                 @Positive Double muscleMassPercent, @Positive Double waistCm,
                                 @Positive Double hipCm, @Positive Double chestCm,
                                 @Positive Double armCm, @Positive Double thighCm) {

}
