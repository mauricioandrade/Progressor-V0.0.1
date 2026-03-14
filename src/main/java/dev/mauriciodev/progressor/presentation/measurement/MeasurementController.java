package dev.mauriciodev.progressor.presentation.measurement;

import dev.mauriciodev.progressor.application.measurement.MeasurementEvolutionResponse;
import dev.mauriciodev.progressor.application.measurement.MeasurementMapper;
import dev.mauriciodev.progressor.application.measurement.MeasurementRequest;
import dev.mauriciodev.progressor.application.measurement.MeasurementResponse;
import dev.mauriciodev.progressor.application.measurement.MeasurementService;
import dev.mauriciodev.progressor.domain.measurement.Measurement;
import dev.mauriciodev.progressor.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/measurements")
@Tag(name = "Measurements", description = "Endpoints for managing student body measurements")
public class MeasurementController {

  private final MeasurementService measurementService;

  public MeasurementController(MeasurementService measurementService) {
    this.measurementService = measurementService;
  }

  @PostMapping
  @PreAuthorize("hasRole('STUDENT')")
  @Operation(summary = "Record a measurement")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Measurement recorded successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid request data"),
      @ApiResponse(responseCode = "404", description = "Student not found")})
  public ResponseEntity<MeasurementResponse> record(@Valid @RequestBody MeasurementRequest request,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Measurement saved = measurementService.record(user.getId(), request);
    return ResponseEntity.status(HttpStatus.CREATED).body(MeasurementMapper.toResponse(saved));
  }

  @GetMapping
  @PreAuthorize("hasRole('STUDENT')")
  @Operation(summary = "List measurements for the authenticated student")
  @ApiResponse(responseCode = "200", description = "Measurements listed successfully")
  public ResponseEntity<List<MeasurementResponse>> findAll(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    List<MeasurementResponse> response = measurementService.findAllForStudent(user.getId()).stream()
        .map(MeasurementMapper::toResponse).toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/evolution")
  @PreAuthorize("hasRole('STUDENT')")
  @Operation(summary = "Get evolution for the authenticated student")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Evolution calculated successfully"),
      @ApiResponse(responseCode = "400", description = "Not enough measurements"),
      @ApiResponse(responseCode = "404", description = "Student not found")})
  public ResponseEntity<MeasurementEvolutionResponse> getEvolution(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    return ResponseEntity.ok(measurementService.getEvolution(user.getId()));
  }

  @GetMapping("/students/{studentId}")
  @PreAuthorize("hasAnyRole('TRAINER', 'ADMIN')")
  @Operation(summary = "List measurements for a student (trainer access)")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Measurements listed successfully"),
      @ApiResponse(responseCode = "403", description = "Access denied"),
      @ApiResponse(responseCode = "404", description = "Student not found")})
  public ResponseEntity<List<MeasurementResponse>> findByStudent(@PathVariable Long studentId) {
    List<MeasurementResponse> response = measurementService.findAllForStudentById(studentId)
        .stream().map(MeasurementMapper::toResponse).toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/students/{studentId}/evolution")
  @PreAuthorize("hasAnyRole('TRAINER', 'ADMIN')")
  @Operation(summary = "Get evolution for a student (trainer access)")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Evolution calculated successfully"),
      @ApiResponse(responseCode = "403", description = "Access denied"),
      @ApiResponse(responseCode = "400", description = "Not enough measurements"),
      @ApiResponse(responseCode = "404", description = "Student not found")})
  public ResponseEntity<MeasurementEvolutionResponse> getEvolutionByStudent(
      @PathVariable Long studentId) {
    return ResponseEntity.ok(measurementService.getEvolutionByStudentId(studentId));
  }
}