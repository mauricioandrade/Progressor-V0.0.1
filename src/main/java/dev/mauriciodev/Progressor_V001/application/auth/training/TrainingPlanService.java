package dev.mauriciodev.Progressor_V001.application.auth.training;

import dev.mauriciodev.Progressor_V001.application.student.StudentService;
import dev.mauriciodev.Progressor_V001.domain.student.Student;
import dev.mauriciodev.Progressor_V001.domain.training.TrainingPlan;
import dev.mauriciodev.Progressor_V001.infrastructure.persistence.TrainingPlanRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TrainingPlanService {

  private final TrainingPlanRepository trainingPlanRepository;
  private final StudentService studentService;

  public TrainingPlanService(TrainingPlanRepository trainingPlanRepository,
      StudentService studentService) {
    this.trainingPlanRepository = trainingPlanRepository;
    this.studentService = studentService;
  }

  public TrainingPlan create(TrainingPlan trainingPlan) {
    return trainingPlanRepository.save(trainingPlan);
  }

  public TrainingPlan findById(Long id) {
    return trainingPlanRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("TrainingPlan not found with id: " + id));
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
}
