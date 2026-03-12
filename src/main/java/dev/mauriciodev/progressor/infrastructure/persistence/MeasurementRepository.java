package dev.mauriciodev.progressor.infrastructure.persistence;

import dev.mauriciodev.progressor.domain.measurement.Measurement;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

  List<Measurement> findByStudentIdOrderByRecordedAtAsc(Long studentId);
}