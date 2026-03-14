package dev.mauriciodev.progressor.presentation.exception;

import dev.mauriciodev.progressor.domain.measurement.MeasurementNotFoundException;
import dev.mauriciodev.progressor.domain.nutrition.DietPlanNotFoundException;
import dev.mauriciodev.progressor.domain.nutritionist.NutritionistNotFoundException;
import dev.mauriciodev.progressor.domain.student.StudentNotFoundException;
import dev.mauriciodev.progressor.domain.trainer.TrainerNotFoundException;
import dev.mauriciodev.progressor.domain.training.ExerciseNotFoundException;
import dev.mauriciodev.progressor.domain.training.TrainingPlanNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
      HttpServletRequest request) {
    String errors = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining(", "));
    return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed: " + errors, request);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex,
      HttpServletRequest request) {
    return buildResponse(HttpStatus.CONFLICT, "Data integrity violation (possibly duplicate entry)",
        request);
  }

  @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
  public ResponseEntity<ErrorResponse> handleBusinessErrors(RuntimeException ex,
      HttpServletRequest request) {
    return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
  }

  @ExceptionHandler({StudentNotFoundException.class, TrainerNotFoundException.class,
      TrainingPlanNotFoundException.class, MeasurementNotFoundException.class,
      ExerciseNotFoundException.class, DietPlanNotFoundException.class,
      NutritionistNotFoundException.class})
  public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex,
      HttpServletRequest request) {
    return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneral(Exception ex, HttpServletRequest request) {
    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected server error occurred",
        request);
  }

  private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message,
      HttpServletRequest request) {
    ErrorResponse response = new ErrorResponse(LocalDateTime.now(), status.value(), message,
        request.getRequestURI());
    return ResponseEntity.status(status).body(response);
  }
}