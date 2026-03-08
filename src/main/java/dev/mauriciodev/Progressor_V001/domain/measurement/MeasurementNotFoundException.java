package dev.mauriciodev.Progressor_V001.domain.measurement;

import java.util.UUID;

public class MeasurementNotFoundException extends RuntimeException {

  public MeasurementNotFoundException(UUID id) {
    super("Measurement not found with id: " + id);
  }
}
