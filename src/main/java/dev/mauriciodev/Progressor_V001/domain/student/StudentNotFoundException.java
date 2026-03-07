package dev.mauriciodev.Progressor_V001.domain.student;

import java.util.UUID;

public class StudentNotFoundException extends RuntimeException {

  public StudentNotFoundException(Long id) {
    super("Student not found with id: " + id);
  }

  public StudentNotFoundException(UUID userId) {
    super("Student not found for user: " + userId);
  }
}