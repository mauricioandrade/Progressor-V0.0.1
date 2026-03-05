package dev.mauriciodev.Progressor_V001.domain.entity;

import dev.mauriciodev.Progressor_V001.domain.enums.Goal;
import dev.mauriciodev.Progressor_V001.domain.enums.TrainingLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

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

  @ManyToOne
  @JoinColumn(name = "current_training_plan_id")
  private TrainingPlan currentTrainingPlan;

  @OneToMany
  @JoinTable(name = "student_training_history", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "training_plan_id"))
  private List<TrainingPlan> trainingHistory = new ArrayList<>();

  public Student(Long id, String name, String email, String phone, Integer age, Double weight,
      Double height, Goal goal, TrainingLevel trainingLevel) {
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

  public TrainingPlan getCurrentTrainingPlan() {
    return currentTrainingPlan;
  }

  public void setCurrentTrainingPlan(TrainingPlan currentTrainingPlan) {
    this.currentTrainingPlan = currentTrainingPlan;
  }

  public List<TrainingPlan> getTrainingHistory() {
    return trainingHistory;
  }

  public void addToHistory(TrainingPlan plan) {
    this.trainingHistory.add(plan);
  }
}