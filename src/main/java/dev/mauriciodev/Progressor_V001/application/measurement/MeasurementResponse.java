package dev.mauriciodev.Progressor_V001.application.measurement;

import java.time.LocalDateTime;
import java.util.UUID;

public record MeasurementResponse(UUID id, Long studentId, LocalDateTime recordedAt,
                                  Double weightKg, Double heightCm, Double bodyFatPercent,
                                  Double muscleMassPercent, Double waistCm, Double hipCm,
                                  Double chestCm, Double armCm, Double thighCm) {

}
