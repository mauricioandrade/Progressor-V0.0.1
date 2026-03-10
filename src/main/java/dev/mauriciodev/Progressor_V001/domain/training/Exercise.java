package dev.mauriciodev.Progressor_V001.domain.training;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Exercise {

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "video_url")
  private String videoUrl;

  protected Exercise() {
  }

  public Exercise(String name, String videoUrl) {
    this.name = name;
    this.videoUrl = videoUrl;
  }

  public String getName() {
    return name;
  }

  public String getVideoUrl() {
    return videoUrl;
  }
}