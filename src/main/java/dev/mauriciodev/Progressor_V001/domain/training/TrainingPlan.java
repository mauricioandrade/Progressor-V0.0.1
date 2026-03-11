package dev.mauriciodev.Progressor_V001.domain.training;

import dev.mauriciodev.Progressor_V001.domain.shared.TrainingLevel;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "training_plans")
public class TrainingPlan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(name = "duration_weeks", nullable = false)
  private Integer durationWeeks;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TrainingLevel level;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
      name = "training_plan_exercises",
      joinColumns = @JoinColumn(name = "training_plan_id")
  )
  private List<Exercise> exercises;

  protected TrainingPlan() {
  }

  public TrainingPlan(Long id, String name, Integer durationWeeks, TrainingLevel level,
      List<Exercise> exercises) {
    this.id = id;
    this.name = name;
    this.durationWeeks = durationWeeks;
    this.level = level;
    this.exercises = exercises;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getDurationWeeks() {
    return durationWeeks;
  }

  public void setDurationWeeks(Integer durationWeeks) {
    this.durationWeeks = durationWeeks;
  }

  public TrainingLevel getLevel() {
    return level;
  }

  public void setLevel(TrainingLevel level) {
    this.level = level;
  }

  public List<Exercise> getExercises() {
    return exercises;
  }

  public void setExercises(List<Exercise> exercises) {
    this.exercises = exercises;
  }
}