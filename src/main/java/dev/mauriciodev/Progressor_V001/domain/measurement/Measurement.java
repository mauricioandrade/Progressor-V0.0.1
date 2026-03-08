package dev.mauriciodev.Progressor_V001.domain.measurement;

import dev.mauriciodev.Progressor_V001.domain.student.Student;
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
  private Double waistCm;
  private Double hipCm;
  private Double chestCm;
  private Double armCm;
  private Double thighCm;

  protected Measurement() {
  }

  public Measurement(Student student, LocalDateTime recordedAt, Double weightKg, Double heightCm,
      Double bodyFatPercent, Double muscleMassPercent, Double waistCm, Double hipCm, Double chestCm,
      Double armCm, Double thighCm) {
    this.student = student;
    this.recordedAt = recordedAt;
    this.weightKg = weightKg;
    this.heightCm = heightCm;
    this.bodyFatPercent = bodyFatPercent;
    this.muscleMassPercent = muscleMassPercent;
    this.waistCm = waistCm;
    this.hipCm = hipCm;
    this.chestCm = chestCm;
    this.armCm = armCm;
    this.thighCm = thighCm;
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

  public Double getWaistCm() {
    return waistCm;
  }

  public Double getHipCm() {
    return hipCm;
  }

  public Double getChestCm() {
    return chestCm;
  }

  public Double getArmCm() {
    return armCm;
  }

  public Double getThighCm() {
    return thighCm;
  }
}