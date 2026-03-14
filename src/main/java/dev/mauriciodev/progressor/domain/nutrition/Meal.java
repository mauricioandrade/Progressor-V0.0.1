package dev.mauriciodev.progressor.domain.nutrition;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Meal {

  @Column(name = "meal_name", nullable = false)
  private String name;

  @Column(name = "meal_description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "meal_calories")
  private Integer calories;

  @Column(name = "meal_time", length = 10)
  private String time;

  protected Meal() {
  }

  public Meal(String name, String description, Integer calories, String time) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Meal name must not be blank");
    }
    this.name = name;
    this.description = description;
    this.calories = calories;
    this.time = time;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Integer getCalories() {
    return calories;
  }

  public String getTime() {
    return time;
  }
}