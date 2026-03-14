package dev.mauriciodev.progressor.domain.measurement;

public record MeasurementDelta(double weightKg, double bodyFatPercent, double muscleMassPercent,
                               double rightBicepsCm, double leftBicepsCm, double chestCm,
                               double abdomenCm, double hipCm, double rightThighCm,
                               double leftThighCm, double rightCalfCm, double leftCalfCm) {

}