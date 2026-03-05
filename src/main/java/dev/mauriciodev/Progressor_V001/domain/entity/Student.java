package dev.mauriciodev.Progressor_V001.domain.entity;

import dev.mauriciodev.Progressor_V001.domain.enums.Goal;
import dev.mauriciodev.Progressor_V001.domain.enums.TrainingLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends Person {

  @Column(nullable = false)
  private Integer age;

  @Column(nullable = false)
  private Double weight;

  @Column(nullable = false)
  private Double height;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Goal goal;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TrainingLevel trainingLevel;

  public Student(Long id, String name, String email, String phone,
      Integer age, Double weight, Double height,
      Goal goal, TrainingLevel trainingLevel) {
    super(id, name, email, phone);
    this.age = age;
    this.weight = weight;
    this.height = height;
    this.goal = goal;
    this.trainingLevel = trainingLevel;
  }

  public Integer getAge() {
    return age;
  }

  public Double getWeight() {
    return weight;
  }

  public Double getHeight() {
    return height;
  }

  public Goal getGoal() {
    return goal;
  }

  public TrainingLevel getTrainingLevel() {
    return trainingLevel;
  }
}