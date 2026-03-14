package dev.mauriciodev.progressor.application.training;

import dev.mauriciodev.progressor.application.student.StudentService;
import dev.mauriciodev.progressor.domain.student.Student;
import dev.mauriciodev.progressor.domain.student.StudentNotFoundException;
import dev.mauriciodev.progressor.domain.training.Exercise;
import dev.mauriciodev.progressor.domain.training.TrainingPlan;
import dev.mauriciodev.progressor.domain.training.TrainingPlanNotFoundException;
import dev.mauriciodev.progressor.infrastructure.persistence.StudentRepository;
import dev.mauriciodev.progressor.infrastructure.persistence.TrainingPlanRepository;
import jakarta.transaction.Transactional;
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

    List<Exercise> exercises = toExercises(request.exercises());

    TrainingPlan plan = new TrainingPlan(null, request.name(), request.durationWeeks(),
        request.level(), exercises);
    TrainingPlan saved = trainingPlanRepository.save(plan);

    student.assignTrainingPlan(saved);
    studentRepository.save(student);

    return saved;
  }

  @Transactional
  public TrainingPlan update(Long id, TrainingPlanRequest request) {
    TrainingPlan plan = findById(id);
    List<Exercise> newExercises = toExercises(request.exercises());
    plan.update(request.name(), request.durationWeeks(), request.level(), newExercises);
    return trainingPlanRepository.save(plan);
  }

  @Transactional
  public void delete(Long id) {
    TrainingPlan plan = findById(id);
    List<Student> affected = studentRepository.findByCurrentTrainingPlanId(id);

    for (Student student : affected) {
      student.assignTrainingPlan(null);
      studentRepository.save(student);
    }

    trainingPlanRepository.delete(plan);
  }

  public TrainingPlan findCurrentForStudent(UUID userId) {
    Student student = studentRepository.findByUserId(userId)
        .orElseThrow(() -> new StudentNotFoundException(userId));
    if (student.getCurrentTrainingPlan() == null) {
      throw new TrainingPlanNotFoundException(0L);
    }
    return student.getCurrentTrainingPlan();
  }

  public TrainingPlan findCurrentForStudentById(Long studentId) {
    Student student = studentRepository.findById(studentId)
        .orElseThrow(() -> new StudentNotFoundException(studentId));
    if (student.getCurrentTrainingPlan() == null) {
      throw new TrainingPlanNotFoundException(0L);
    }
    return student.getCurrentTrainingPlan();
  }

  @Transactional
  public List<TrainingPlan> findHistoryForStudent(UUID userId) {
    Student student = studentRepository.findByUserId(userId)
        .orElseThrow(() -> new StudentNotFoundException(userId));
    return student.getTrainingHistory();
  }

  private List<Exercise> toExercises(List<ExerciseRequest> requests) {
    return requests.stream()
        .map(e -> new Exercise(e.name(), e.videoUrl(), e.sets(), e.repetitions(), e.notes()))
        .toList();
  }
}