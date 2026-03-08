package dev.mauriciodev.Progressor_V001.application.measurement;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MeasurementRequest(@NotNull @Positive Double weightKg,
                                 @NotNull @Positive Double heightCm,
                                 @NotNull @Positive Double bodyFatPercent,
                                 @NotNull @Positive Double muscleMassPercent,
                                 @NotNull @Positive Double rightBicepsCm,
                                 @NotNull @Positive Double leftBicepsCm,
                                 @NotNull @Positive Double chestCm,
                                 @NotNull @Positive Double abdomenCm,
                                 @NotNull @Positive Double hipCm,
                                 @NotNull @Positive Double rightThighCm,
                                 @NotNull @Positive Double leftThighCm,
                                 @NotNull @Positive Double rightCalfCm,
                                 @NotNull @Positive Double leftCalfCm) {

}
