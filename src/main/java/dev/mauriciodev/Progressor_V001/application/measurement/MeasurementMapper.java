package dev.mauriciodev.Progressor_V001.application.measurement;

import dev.mauriciodev.Progressor_V001.domain.measurement.Measurement;

public class MeasurementMapper {

  private MeasurementMapper() {
  }

  public static MeasurementResponse toResponse(Measurement m) {
    return new MeasurementResponse(m.getId(), m.getStudent().getId(), m.getRecordedAt(),
        m.getWeightKg(), m.getHeightCm(), m.getBodyFatPercent(), m.getMuscleMassPercent(),
        m.getWaistCm(), m.getHipCm(), m.getChestCm(), m.getArmCm(), m.getThighCm());
  }
}
