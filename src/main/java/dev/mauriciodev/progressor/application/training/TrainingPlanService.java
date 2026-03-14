package dev.mauriciodev.progressor.application.training;

import dev.mauriciodev.progressor.application.student.StudentService;
import dev.mauriciodev.progressor.domain.student.Student;
import dev.mauriciodev.progressor.domain.student.StudentNotFoundException;
import dev.mauriciodev.progressor.domain.training.Exercise;
import dev.mauriciodev.progressor.domain.training.TrainingPlan;
import dev.mauriciodev.progressor.domain.training.TrainingPlanNotFoundException;
import dev.mauriciodev.progressor.infrastructure.persistence.StudentRepository;
import dev.mauriciodev.progressor.infrastructure.persistence.TrainingPlanRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class TrainingPlanService {

  private final TrainingPlanRepository trainingPlanRepository;
  private final StudentService studentService;
  private final StudentRepository studentRepository;

  public TrainingPlanService(TrainingPlanRepository trainingPlanRepository,
      StudentService studentService, StudentRepository studentRepository) {
    this.trainingPlanRepository = trainingPlanRepository;
    this.studentService = studentService;
    this.studentRepository = studentRepository;
  }

  public TrainingPlan create(TrainingPlan trainingPlan) {
    return trainingPlanRepository.save(trainingPlan);
  }

  public TrainingPlan findById(Long id) {
    return trainingPlanRepository.findById(id)
        .orElseThrow(() -> new TrainingPlanNotFoundException(id));
  }

  public List<TrainingPlan> findAll() {
    return trainingPlanRepository.findAll();
  }

  @Transactional
  public Student assignToStudent(Long planId, Long studentId) {
    TrainingPlan plan = findById(planId);
    Student student = studentService.findById(studentId);
    student.assignTrainingPlan(plan);
    return studentRepository.save(student);
  }

  @Transactional
  public TrainingPlan createForStudent(UUID trainerUserId, TrainingPlanRequest request) {
    Student student = studentRepository.findById(request.studentId())
        .orElseThrow(() -> new StudentNotFoundException(request.studentId()));

    List<Exercise> exercises = request.exercises().stream()
        .map(e -> new Exercise(e.name(), e.videoUrl(), e.sets(), e.repetitions(), e.notes()))
        .toList();

    TrainingPlan plan = new TrainingPlan(null, request.name(), request.durationWeeks(),
        request.level(), exercises);
    TrainingPlan saved = trainingPlanRepository.save(plan);

    student.assignTrainingPlan(saved);
    studentRepository.save(student);

    return saved;
  }

  @Transactional
  public TrainingPlan update(Long id, TrainingPlanUpdateRequest request) {
    TrainingPlan plan = findById(id);

    plan.setName(request.name());
    plan.setDurationWeeks(request.durationWeeks());
    plan.setLevel(request.level());

    List<Exercise> newExercises = request.exercises().stream()
        .map(e -> new Exercise(e.name(), e.videoUrl(), e.sets(), e.repetitions(), e.notes()))
        .toList();

    plan.getExercises().clear();
    plan.getExercises().addAll(newExercises);

    return trainingPlanRepository.save(plan);
  }

  @Transactional
  public void delete(Long id) {
    TrainingPlan plan = findById(id);
    List<Student> students = studentRepository.findAll();

    for (Student student : students) {
      boolean modified = false;
      if (student.getCurrentTrainingPlan() != null && student.getCurrentTrainingPlan().getId()
          .equals(id)) {
        student.assignTrainingPlan(null); // Usando o método de domínio
        modified = true;
      }
      if (modified) {
        studentRepository.save(student);
      }
    }

    trainingPlanRepository.delete(plan);
  }

  public TrainingPlan findCurrentForStudent(UUID userId) {
    Student student = studentRepository.findByUserId(userId)
        .orElseThrow(() -> new StudentNotFoundException(userId));
    if (student.getCurrentTrainingPlan() == null) {
      throw new TrainingPlanNotFoundException("Student has no active training plan");
    }
    return student.getCurrentTrainingPlan();
  }

  public TrainingPlan findCurrentForStudentById(Long studentId) {
    Student student = studentRepository.findById(studentId)
        .orElseThrow(() -> new StudentNotFoundException(studentId));
    if (student.getCurrentTrainingPlan() == null) {
      throw new TrainingPlanNotFoundException("Student has no active training plan");
    }
    return student.getCurrentTrainingPlan();
  }

  @Transactional
  public List<TrainingPlan> findHistoryForStudent(UUID userId) {
    Student student = studentRepository.findByUserId(userId)
        .orElseThrow(() -> new StudentNotFoundException(userId));
    return student.getTrainingHistory();
  }
}