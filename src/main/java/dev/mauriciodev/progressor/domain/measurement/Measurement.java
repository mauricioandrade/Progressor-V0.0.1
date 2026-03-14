package dev.mauriciodev.progressor.domain.measurement;

import dev.mauriciodev.progressor.domain.student.Student;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "measurements")
public class Measurement {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

  @Column(nullable = false)
  private LocalDateTime recordedAt;

  private Double weightKg;
  private Double heightCm;
  private Double bodyFatPercent;
  private Double muscleMassPercent;
  private Double rightBicepsCm;
  private Double leftBicepsCm;
  private Double chestCm;
  private Double abdomenCm;
  private Double hipCm;
  private Double rightThighCm;
  private Double leftThighCm;
  private Double rightCalfCm;
  private Double leftCalfCm;

  public Measurement() {
  }

  public Measurement(UUID id, Student student, LocalDateTime recordedAt, Double weightKg,
      Double heightCm, Double bodyFatPercent, Double muscleMassPercent, Double rightBicepsCm,
      Double leftBicepsCm, Double chestCm, Double abdomenCm, Double hipCm, Double rightThighCm,
      Double leftThighCm, Double rightCalfCm, Double leftCalfCm) {
    if (student == null) {
      throw new IllegalArgumentException("Student must not be null");
    }
    if (recordedAt == null) {
      throw new IllegalArgumentException("RecordedAt must not be null");
    }
    if (weightKg != null && weightKg <= 0) {
      throw new IllegalArgumentException("Weight must be positive");
    }
    if (heightCm != null && heightCm <= 0) {
      throw new IllegalArgumentException("Height must be positive");
    }
    this.id = id;
    this.student = student;
    this.recordedAt = recordedAt;
    this.weightKg = weightKg;
    this.heightCm = heightCm;
    this.bodyFatPercent = bodyFatPercent;
    this.muscleMassPercent = muscleMassPercent;
    this.rightBicepsCm = rightBicepsCm;
    this.leftBicepsCm = leftBicepsCm;
    this.chestCm = chestCm;
    this.abdomenCm = abdomenCm;
    this.hipCm = hipCm;
    this.rightThighCm = rightThighCm;
    this.leftThighCm = leftThighCm;
    this.rightCalfCm = rightCalfCm;
    this.leftCalfCm = leftCalfCm;
  }

  public MeasurementDelta calculateEvolutionFrom(Measurement baseline) {
    if (baseline == null) {
      throw new IllegalArgumentException("Baseline measurement must not be null");
    }
    return new MeasurementDelta(round(this.weightKg, baseline.weightKg),
        round(this.bodyFatPercent, baseline.bodyFatPercent),
        round(this.muscleMassPercent, baseline.muscleMassPercent),
        round(this.rightBicepsCm, baseline.rightBicepsCm),
        round(this.leftBicepsCm, baseline.leftBicepsCm), round(this.chestCm, baseline.chestCm),
        round(this.abdomenCm, baseline.abdomenCm), round(this.hipCm, baseline.hipCm),
        round(this.rightThighCm, baseline.rightThighCm),
        round(this.leftThighCm, baseline.leftThighCm),
        round(this.rightCalfCm, baseline.rightCalfCm), round(this.leftCalfCm, baseline.leftCalfCm));
  }

  private double round(Double current, Double baseline) {
    if (current == null || baseline == null) {
      return 0.0;
    }
    return Math.round((current - baseline) * 10.0) / 10.0;
  }

  public UUID getId() {
    return id;
  }

  public Student getStudent() {
    return student;
  }

  public LocalDateTime getRecordedAt() {
    return recordedAt;
  }

  public Double getWeightKg() {
    return weightKg;
  }

  public Double getHeightCm() {
    return heightCm;
  }

  public Double getBodyFatPercent() {
    return bodyFatPercent;
  }

  public Double getMuscleMassPercent() {
    return muscleMassPercent;
  }

  public Double getRightBicepsCm() {
    return rightBicepsCm;
  }

  public Double getLeftBicepsCm() {
    return leftBicepsCm;
  }

  public Double getChestCm() {
    return chestCm;
  }

  public Double getAbdomenCm() {
    return abdomenCm;
  }

  public Double getHipCm() {
    return hipCm;
  }

  public Double getRightThighCm() {
    return rightThighCm;
  }

  public Double getLeftThighCm() {
    return leftThighCm;
  }

  public Double getRightCalfCm() {
    return rightCalfCm;
  }

  public Double getLeftCalfCm() {
    return leftCalfCm;
  }
}