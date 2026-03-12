package dev.mauriciodev.progressor.application.measurement;

public record MeasurementEvolutionResponse(MeasurementResponse first, MeasurementResponse last,
                                           double weightKgDelta, double bodyFatPercentDelta,
                                           double muscleMassPercentDelta, double rightBicepsCmDelta,
                                           double leftBicepsCmDelta, double chestCmDelta,
                                           double abdomenCmDelta, double hipCmDelta,
                                           double rightThighCmDelta, double leftThighCmDelta,
                                           double rightCalfCmDelta, double leftCalfCmDelta) {

}
