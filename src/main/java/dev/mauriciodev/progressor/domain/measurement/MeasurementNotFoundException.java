package dev.mauriciodev.progressor.domain.measurement;

import java.util.UUID;

public class MeasurementNotFoundException extends RuntimeException {

  public MeasurementNotFoundException(UUID id) {
    super("Measurement not found with id: " + id);
  }
}
