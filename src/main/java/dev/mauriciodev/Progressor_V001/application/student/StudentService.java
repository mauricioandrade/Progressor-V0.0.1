package dev.mauriciodev.Progressor_V001.application.student;

import dev.mauriciodev.Progressor_V001.domain.student.Student;
import dev.mauriciodev.Progressor_V001.domain.student.StudentNotFoundException;
import dev.mauriciodev.Progressor_V001.domain.training.TrainingPlan;
import dev.mauriciodev.Progressor_V001.domain.shared.TrainingLevel;
import dev.mauriciodev.Progressor_V001.infrastructure.persistence.StudentRepository;
import dev.mauriciodev.Progressor_V001.infrastructure.persistence.TrainingPlanRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

  private final StudentRepository studentRepository;
  private final TrainingPlanRepository trainingPlanRepository;

  public StudentService(StudentRepository studentRepository,
      TrainingPlanRepository trainingPlanRepository) {
    this.studentRepository = studentRepository;
    this.trainingPlanRepository = trainingPlanRepository;
  }

  public Student findByUserId(UUID userId) {
    return studentRepository.findByUserId(userId)
        .orElseThrow(() -> new StudentNotFoundException(userId));
  }

  public Student register(Student student) {
    return studentRepository.save(student);
  }

  @Transactional
  public Student findById(Long id) {
    return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
  }

  public List<Student> findAll() {
    return studentRepository.findAll();
  }

  @Transactional
  public Student evolveProgress(UUID userId) {
    Student student = findByUserId(userId);
    student.evolve();
    return studentRepository.save(student);
  }

  @Transactional
  public Student progress(Long id) {
    Student student = findById(id);

    if (student.getTrainingLevel() == TrainingLevel.ADVANCED) {
      throw new IllegalStateException("Student is already at the highest level: ADVANCED");
    }

    if (student.getCurrentTrainingPlan() != null) {
      student.addToHistory(student.getCurrentTrainingPlan());
    }

    student.evolve();

    List<TrainingPlan> plansForNewLevel = trainingPlanRepository.findByLevel(
        student.getTrainingLevel());

    if (!plansForNewLevel.isEmpty()) {
      student.setCurrentTrainingPlan(plansForNewLevel.get(0));
    }

    return studentRepository.save(student);
  }

  @Transactional
  public Student update(UUID userId, StudentUpdateRequest request) {
    Student student = findByUserId(userId);

    if (request.name() != null) {
      student.setName(request.name());
    }
    if (request.phone() != null) {
      student.setPhone(request.phone());
    }
    if (request.age() != null) {
      student.setAge(request.age());
    }
    if (request.weight() != null) {
      student.setWeight(request.weight());
    }
    if (request.height() != null) {
      student.setHeight(request.height());
    }
    if (request.goal() != null) {
      student.setGoal(request.goal());
    }

    return studentRepository.save(student);
  }
}