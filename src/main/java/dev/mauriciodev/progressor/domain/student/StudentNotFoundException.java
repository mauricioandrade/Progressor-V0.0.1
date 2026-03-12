package dev.mauriciodev.progressor.domain.student;

import java.util.UUID;

public class StudentNotFoundException extends RuntimeException {

  public StudentNotFoundException(Long id) {
    super("Student not found with id: " + id);
  }

  public StudentNotFoundException(UUID userId) {
    super("Student not found for user: " + userId);
  }
}