package dev.mauriciodev.Progressor_V001.controller;

import dev.mauriciodev.Progressor_V001.domain.entity.Student;
import dev.mauriciodev.Progressor_V001.dto.request.StudentRequest;
import dev.mauriciodev.Progressor_V001.dto.response.StudentResponse;
import dev.mauriciodev.Progressor_V001.service.StudentService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @PostMapping
  public ResponseEntity<StudentResponse> register(@RequestBody StudentRequest request) {
    Student student = new Student(
        null,
        request.name(),
        request.email(),
        request.phone(),
        request.age(),
        request.weight(),
        request.height(),
        request.goal(),
        request.trainingLevel()
    );
    Student saved = studentService.register(student);
    return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
  }

  @GetMapping
  public ResponseEntity<List<StudentResponse>> findAll() {
    List<StudentResponse> response = studentService.findAll()
        .stream()
        .map(this::toResponse)
        .toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<StudentResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(toResponse(studentService.findById(id)));
  }

  private StudentResponse toResponse(Student student) {
    return new StudentResponse(
        student.getId(),
        student.getName(),
        student.getEmail(),
        student.getPhone(),
        student.getAge(),
        student.getWeight(),
        student.getHeight(),
        student.getGoal(),
        student.getTrainingLevel(),
        student.getCurrentTrainingPlan() != null
            ? student.getCurrentTrainingPlan().getName()
            : null
    );
  }
}