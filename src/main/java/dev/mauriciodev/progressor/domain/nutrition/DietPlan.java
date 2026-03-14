package dev.mauriciodev.progressor.domain.nutrition;

import dev.mauriciodev.progressor.domain.shared.DietFocus;
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
@Table(name = "diet_plans")
public class DietPlan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(name = "duration_weeks", nullable = false)
  private Integer durationWeeks;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private DietFocus focus;

  @Column(name = "daily_calories")
  private Integer dailyCalories;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "diet_plan_meals", joinColumns = @JoinColumn(name = "diet_plan_id"))
  private List<Meal> meals = new ArrayList<>();

  protected DietPlan() {
  }

  public DietPlan(Long id, String name, Integer durationWeeks, DietFocus focus,
      Integer dailyCalories, List<Meal> meals) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Diet plan name must not be blank");
    }
    if (durationWeeks == null || durationWeeks <= 0) {
      throw new IllegalArgumentException("Duration must be a positive number of weeks");
    }
    if (focus == null) {
      throw new IllegalArgumentException("Diet focus must not be null");
    }
    this.id = id;
    this.name = name;
    this.durationWeeks = durationWeeks;
    this.focus = focus;
    this.dailyCalories = dailyCalories;
    this.meals = meals != null ? new ArrayList<>(meals) : new ArrayList<>();
  }

  public void update(String name, Integer durationWeeks, DietFocus focus, Integer dailyCalories,
      List<Meal> meals) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Diet plan name must not be blank");
    }
    if (durationWeeks == null || durationWeeks <= 0) {
      throw new IllegalArgumentException("Duration must be a positive number of weeks");
    }
    if (focus == null) {
      throw new IllegalArgumentException("Diet focus must not be null");
    }
    if (meals == null) {
      throw new IllegalArgumentException("Meals list must not be null");
    }
    this.name = name;
    this.durationWeeks = durationWeeks;
    this.focus = focus;
    this.dailyCalories = dailyCalories;
    this.meals = new ArrayList<>(meals);
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

  public DietFocus getFocus() {
    return focus;
  }

  public Integer getDailyCalories() {
    return dailyCalories;
  }

  public List<Meal> getMeals() {
    return Collections.unmodifiableList(meals);
  }
}