package dev.mauriciodev.Progressor_V001.domain.student;

public class StudentNotFoundException extends RuntimeException {

  public StudentNotFoundException(Long id) {
    super("Student not found with id: " + id);
  }
}