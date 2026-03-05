package dev.mauriciodev.Progressor_V001.domain.entity;

import dev.mauriciodev.Progressor_V001.domain.enums.TrainingLevel;
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

  @Column(nullable = false)
  private Integer durationWeeks;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TrainingLevel level;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "training_plan_exercises",
      joinColumns = @JoinColumn(name = "training_plan_id"))
  @Column(name = "exercise")
  private List<String> exercises;

  protected TrainingPlan() {
  }

  public TrainingPlan(Long id, String name, Integer durationWeeks,
      TrainingLevel level, List<String> exercises) {
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

  public Integer getDurationWeeks() {
    return durationWeeks;
  }

  public TrainingLevel getLevel() {
    return level;
  }

  public List<String> getExercises() {
    return exercises;
  }
}

