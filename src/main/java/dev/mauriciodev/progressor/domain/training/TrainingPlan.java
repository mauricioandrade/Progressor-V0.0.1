package dev.mauriciodev.progressor.domain.training;

import dev.mauriciodev.progressor.domain.shared.TrainingLevel;
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
import java.util.ArrayList;
import java.util.Collections;
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
  @CollectionTable(name = "training_plan_exercises", joinColumns = @JoinColumn(name = "training_plan_id"))
  private List<Exercise> exercises = new ArrayList<>();

  protected TrainingPlan() {
  }

  public TrainingPlan(Long id, String name, Integer durationWeeks, TrainingLevel level,
      List<Exercise> exercises) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Training plan name must not be blank");
    }
    if (durationWeeks == null || durationWeeks <= 0) {
      throw new IllegalArgumentException("Duration must be a positive number of weeks");
    }
    if (level == null) {
      throw new IllegalArgumentException("Training level must not be null");
    }
    this.id = id;
    this.name = name;
    this.durationWeeks = durationWeeks;
    this.level = level;
    this.exercises = exercises != null ? new ArrayList<>(exercises) : new ArrayList<>();
  }

  public void update(String name, Integer durationWeeks, TrainingLevel level,
      List<Exercise> exercises) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Training plan name must not be blank");
    }
    if (durationWeeks == null || durationWeeks <= 0) {
      throw new IllegalArgumentException("Duration must be a positive number of weeks");
    }
    if (level == null) {
      throw new IllegalArgumentException("Training level must not be null");
    }
    if (exercises == null) {
      throw new IllegalArgumentException("Exercises list must not be null");
    }
    this.name = name;
    this.durationWeeks = durationWeeks;
    this.level = level;
    this.exercises = new ArrayList<>(exercises);
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getDurationWeeks() {
    return durationWeeks;
  }

  public TrainingLevel getLevel() {
    return level;
  }

  public List<Exercise> getExercises() {
    return Collections.unmodifiableList(exercises);
  }
}