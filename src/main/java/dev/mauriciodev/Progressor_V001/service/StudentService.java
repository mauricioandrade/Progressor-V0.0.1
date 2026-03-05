package dev.mauriciodev.Progressor_V001.service;

import dev.mauriciodev.Progressor_V001.domain.entity.Student;
import dev.mauriciodev.Progressor_V001.domain.entity.TrainingPlan;
import dev.mauriciodev.Progressor_V001.domain.enums.TrainingLevel;
import dev.mauriciodev.Progressor_V001.repository.StudentRepository;
import dev.mauriciodev.Progressor_V001.repository.TrainingPlanRepository;
import java.util.List;
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

  public Student register(Student student) {
    return studentRepository.save(student);
  }

  public Student findById(Long id) {
    return studentRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
  }

  public List<Student> findAll() {
    return studentRepository.findAll();
  }

  public Student progress(Long id) {
    Student student = findById(id);

    if (student.getTrainingLevel() == TrainingLevel.ADVANCED) {
      throw new IllegalStateException("Student is already at the highest level: ADVANCED");
    }

    if (student.getCurrentTrainingPlan() != null) {
      student.addToHistory(student.getCurrentTrainingPlan());
    }

    student.evolve();

    List<TrainingPlan> plansForNewLevel = trainingPlanRepository
        .findByLevel(student.getTrainingLevel());

    if (!plansForNewLevel.isEmpty()) {
      student.setCurrentTrainingPlan(plansForNewLevel.get(0));
    }

    return studentRepository.save(student);
  }
}
