package dev.mauriciodev.Progressor_V001.application.measurement;

import dev.mauriciodev.Progressor_V001.domain.measurement.Measurement;

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
