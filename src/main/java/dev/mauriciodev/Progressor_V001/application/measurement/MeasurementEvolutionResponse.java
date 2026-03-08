package dev.mauriciodev.Progressor_V001.application.measurement;

public record MeasurementEvolutionResponse(MeasurementResponse first, MeasurementResponse last,
                                           Double weightKgDelta, Double bodyFatPercentDelta,
                                           Double muscleMassPercentDelta, Double waistCmDelta,
                                           Double hipCmDelta, Double chestCmDelta,
                                           Double armCmDelta, Double thighCmDelta) {

}