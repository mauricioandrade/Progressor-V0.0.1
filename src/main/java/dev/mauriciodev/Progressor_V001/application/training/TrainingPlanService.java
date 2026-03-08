package dev.mauriciodev.Progressor_V001.application.training;

import dev.mauriciodev.Progressor_V001.application.student.StudentService;
import dev.mauriciodev.Progressor_V001.domain.student.Student;
import dev.mauriciodev.Progressor_V001.domain.student.StudentNotFoundException;
import dev.mauriciodev.Progressor_V001.domain.training.TrainingPlan;
import dev.mauriciodev.Progressor_V001.domain.training.TrainingPlanNotFoundException;
import dev.mauriciodev.Progressor_V001.infrastructure.persistence.StudentRepository;
import dev.mauriciodev.Progressor_V001.infrastructure.persistence.TrainingPlanRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public final class TrainingPlanService {

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

  public Student assignToStudent(Long planId, Long studentId) {
    TrainingPlan plan = findById(planId);
    Student student = studentService.findById(studentId);

    if (student.getCurrentTrainingPlan() != null) {
      student.addToHistory(student.getCurrentTrainingPlan());
    }

    student.setCurrentTrainingPlan(plan);
    return studentService.register(student);
  }

  public TrainingPlan createForStudent(UUID userId, TrainingPlanRequest request) {
    Student student = studentRepository.findByUserId(userId)
        .orElseThrow(() -> new StudentNotFoundException(userId));

    TrainingPlan plan = new TrainingPlan(null, request.name(), request.durationWeeks(),
        request.level(), request.exercises());
    TrainingPlan saved = trainingPlanRepository.save(plan);
    student.setCurrentTrainingPlan(saved);
    studentRepository.save(student);
    return saved;
  }

  public TrainingPlan findCurrentForStudent(UUID userId) {
    Student student = studentRepository.findByUserId(userId)
        .orElseThrow(() -> new StudentNotFoundException(userId));
    if (student.getCurrentTrainingPlan() == null) {
      throw new TrainingPlanNotFoundException(0L);
    }
    return student.getCurrentTrainingPlan();
  }

  public List<TrainingPlan> findHistoryForStudent(UUID userId) {
    Student student = studentRepository.findByUserId(userId)
        .orElseThrow(() -> new StudentNotFoundException(userId));
    return student.getTrainingHistory();
  }
}