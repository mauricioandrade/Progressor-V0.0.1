package dev.mauriciodev.Progressor_V001.application.measurement;

import java.time.LocalDateTime;
import java.util.UUID;

public record MeasurementResponse(UUID id, Long studentId, LocalDateTime recordedAt,
                                  Double weightKg, Double heightCm, Double bodyFatPercent,
                                  Double muscleMassPercent, Double rightBicepsCm,
                                  Double leftBicepsCm, Double chestCm, Double abdomenCm,
                                  Double hipCm, Double rightThighCm, Double leftThighCm,
                                  Double rightCalfCm, Double leftCalfCm) {

}
