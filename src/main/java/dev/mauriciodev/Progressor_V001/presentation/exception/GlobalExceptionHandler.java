package dev.mauriciodev.Progressor_V001.presentation.exception;

import dev.mauriciodev.Progressor_V001.domain.student.StudentNotFoundException;
import dev.mauriciodev.Progressor_V001.domain.trainer.TrainerNotFoundException;
import dev.mauriciodev.Progressor_V001.domain.training.TrainingPlanNotFoundException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(Map.of("error", ex.getMessage()));
  }

  @ExceptionHandler({
      StudentNotFoundException.class,
      TrainerNotFoundException.class,
      TrainingPlanNotFoundException.class
  })
  public ResponseEntity<Map<String, String>> handleNotFound(RuntimeException ex) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(Map.of("error", ex.getMessage()));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Map.of("error", ex.getMessage()));
  }
}