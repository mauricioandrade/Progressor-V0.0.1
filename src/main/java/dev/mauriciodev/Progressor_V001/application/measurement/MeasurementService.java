package dev.mauriciodev.Progressor_V001.application.measurement;

import dev.mauriciodev.Progressor_V001.domain.measurement.Measurement;
import dev.mauriciodev.Progressor_V001.domain.student.Student;
import dev.mauriciodev.Progressor_V001.domain.student.StudentNotFoundException;
import dev.mauriciodev.Progressor_V001.infrastructure.persistence.MeasurementRepository;
import dev.mauriciodev.Progressor_V001.infrastructure.persistence.StudentRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MeasurementService {

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

    Measurement measurement = new Measurement(null, student, LocalDateTime.now(),
        request.weightKg(), request.heightCm(), request.bodyFatPercent(),
        request.muscleMassPercent(), request.rightBicepsCm(), request.leftBicepsCm(),
        request.chestCm(), request.abdomenCm(), request.hipCm(), request.rightThighCm(),
        request.leftThighCm(), request.rightCalfCm(), request.leftCalfCm());

    return measurementRepository.save(measurement);
  }

  public List<Measurement> findAllForStudent(UUID userId) {
    Student student = studentRepository.findByUserId(userId)
        .orElseThrow(() -> new StudentNotFoundException(userId));
    return measurementRepository.findByStudentIdOrderByRecordedAtAsc(student.getId());
  }

  public List<Measurement> findAllForStudentById(Long studentId) {
    studentRepository.findById(studentId)
        .orElseThrow(() -> new StudentNotFoundException(studentId));
    return measurementRepository.findByStudentIdOrderByRecordedAtAsc(studentId);
  }

  public MeasurementEvolutionResponse getEvolution(UUID userId) {
    List<Measurement> list = findAllForStudent(userId);
    return buildEvolution(list);
  }

  public MeasurementEvolutionResponse getEvolutionByStudentId(Long studentId) {
    studentRepository.findById(studentId)
        .orElseThrow(() -> new StudentNotFoundException(studentId));
    List<Measurement> list = measurementRepository.findByStudentIdOrderByRecordedAtAsc(studentId);
    return buildEvolution(list);
  }

  private MeasurementEvolutionResponse buildEvolution(List<Measurement> list) {
    if (list.size() < 2) {
      throw new IllegalStateException(
          "At least 2 measurements are required to calculate evolution.");
    }

    Measurement first = list.get(0);
    Measurement last = list.get(list.size() - 1);

    return new MeasurementEvolutionResponse(MeasurementMapper.toResponse(first),
        MeasurementMapper.toResponse(last), delta(last.getWeightKg(), first.getWeightKg()),
        delta(last.getBodyFatPercent(), first.getBodyFatPercent()),
        delta(last.getMuscleMassPercent(), first.getMuscleMassPercent()),
        delta(last.getRightBicepsCm(), first.getRightBicepsCm()),
        delta(last.getLeftBicepsCm(), first.getLeftBicepsCm()),
        delta(last.getChestCm(), first.getChestCm()),
        delta(last.getAbdomenCm(), first.getAbdomenCm()), delta(last.getHipCm(), first.getHipCm()),
        delta(last.getRightThighCm(), first.getRightThighCm()),
        delta(last.getLeftThighCm(), first.getLeftThighCm()),
        delta(last.getRightCalfCm(), first.getRightCalfCm()),
        delta(last.getLeftCalfCm(), first.getLeftCalfCm()));
  }

  private double delta(Double current, Double previous) {
    if (current == null || previous == null) {
      return 0.0;
    }
    return Math.round((current - previous) * 10.0) / 10.0;
  }
}