package dev.mauriciodev.progressor.domain.training;

public class ExerciseNotFoundException extends RuntimeException {

  public ExerciseNotFoundException(String name) {
    super("Exercise not found with name: " + name);
  }
}