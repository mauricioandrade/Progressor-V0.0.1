package dev.mauriciodev.Progressor_V001.application.measurement;

import dev.mauriciodev.Progressor_V001.domain.measurement.Measurement;
import dev.mauriciodev.Progressor_V001.domain.student.Student;
import dev.mauriciodev.Progressor_V001.domain.student.StudentNotFoundException;
import dev.mauriciodev.Progressor_V001.infrastructure.persistence.MeasurementRepository;
import dev.mauriciodev.Progressor_V001.infrastructure.persistence.StudentRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public final class MeasurementService {

  private final MeasurementRepository measurementRepository;
  private final StudentRepository studentRepository;

  public MeasurementService(MeasurementRepository measurementRepository,
      StudentRepository studentRepository) {
    this.measurementRepository = measurementRepository;
    this.studentRepository = studentRepository;
  }

  @Transactional
  public Measurement record(UUID userId, MeasurementRequest request) {
    Student student = studentRepository.findByUserId(userId)
        .orElseThrow(() -> new StudentNotFoundException(userId));

    LocalDateTime recordedAt =
        request.recordedAt() != null ? request.recordedAt() : LocalDateTime.now();

    Measurement measurement = new Measurement(student, recordedAt, request.weightKg(),
        request.heightCm(), request.bodyFatPercent(), request.muscleMassPercent(),
        request.waistCm(), request.hipCm(), request.chestCm(), request.armCm(), request.thighCm());
    return measurementRepository.save(measurement);
  }

  public List<Measurement> findAllForStudent(UUID userId) {
    Student student = studentRepository.findByUserId(userId)
        .orElseThrow(() -> new StudentNotFoundException(userId));
    return measurementRepository.findByStudentIdOrderByRecordedAtAsc(student.getId());
  }

  public List<Measurement> findAllForStudentById(Long studentId) {
    return measurementRepository.findByStudentIdOrderByRecordedAtAsc(studentId);
  }

  public MeasurementEvolutionResponse getEvolution(UUID userId) {
    List<Measurement> measurements = findAllForStudent(userId);
    if (measurements.size() < 2) {
      throw new IllegalStateException("Not enough measurements to calculate evolution.");
    }

    Measurement first = measurements.getFirst();
    Measurement last = measurements.getLast();

    return new MeasurementEvolutionResponse(MeasurementMapper.toResponse(first),
        MeasurementMapper.toResponse(last), delta(first.getWeightKg(), last.getWeightKg()),
        delta(first.getBodyFatPercent(), last.getBodyFatPercent()),
        delta(first.getMuscleMassPercent(), last.getMuscleMassPercent()),
        delta(first.getWaistCm(), last.getWaistCm()), delta(first.getHipCm(), last.getHipCm()),
        delta(first.getChestCm(), last.getChestCm()), delta(first.getArmCm(), last.getArmCm()),
        delta(first.getThighCm(), last.getThighCm()));
  }

  private Double delta(Double first, Double last) {
    if (first == null || last == null) {
      return null;
    }
    return last - first;
  }
}
