package dev.mauriciodev.Progressor_V001.infrastructure.persistence;

import dev.mauriciodev.Progressor_V001.domain.measurement.Measurement;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

  List<Measurement> findByStudentIdOrderByRecordedAtAsc(Long studentId);
}