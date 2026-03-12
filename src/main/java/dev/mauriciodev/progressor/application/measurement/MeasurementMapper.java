package dev.mauriciodev.progressor.application.measurement;

import dev.mauriciodev.progressor.domain.measurement.Measurement;

public class MeasurementMapper {

  private MeasurementMapper() {
  }

  public static MeasurementResponse toResponse(Measurement m) {
    return new MeasurementResponse(m.getId(), m.getStudent().getId(), m.getRecordedAt(),
        m.getWeightKg(), m.getHeightCm(), m.getBodyFatPercent(), m.getMuscleMassPercent(),
        m.getRightBicepsCm(), m.getLeftBicepsCm(), m.getChestCm(), m.getAbdomenCm(), m.getHipCm(),
        m.getRightThighCm(), m.getLeftThighCm(), m.getRightCalfCm(), m.getLeftCalfCm());
  }
}
