package dev.mauriciodev.Progressor_V001.domain.training;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Exercise {

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "video_url")
  private String videoUrl;

  @Column(name = "sets")
  private Integer sets;

  @Column(name = "repetitions")
  private String repetitions;

  @Column(name = "notes", columnDefinition = "TEXT")
  private String notes;

  protected Exercise() {
  }

  public Exercise(String name, String videoUrl, Integer sets, String repetitions, String notes) {
    this.name = name;
    this.videoUrl = videoUrl;
    this.sets = sets;
    this.repetitions = repetitions;
    this.notes = notes;
  }

  // Getters
  public String getName() {
    return name;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public Integer getSets() {
    return sets;
  }

  public String getRepetitions() {
    return repetitions;
  }

  public String getNotes() {
    return notes;
  }
}