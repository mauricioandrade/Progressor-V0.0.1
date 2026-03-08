package dev.mauriciodev.Progressor_V001.application.trainer;

import dev.mauriciodev.Progressor_V001.domain.student.Student;
import dev.mauriciodev.Progressor_V001.domain.student.StudentNotFoundException;
import dev.mauriciodev.Progressor_V001.domain.trainer.PersonalTrainer;
import dev.mauriciodev.Progressor_V001.domain.trainer.TrainerNotFoundException;
import dev.mauriciodev.Progressor_V001.infrastructure.persistence.PersonalTrainerRepository;
import dev.mauriciodev.Progressor_V001.infrastructure.persistence.StudentRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PersonalTrainerService {

  private final PersonalTrainerRepository personalTrainerRepository;
  private final StudentRepository studentRepository;

  public PersonalTrainerService(PersonalTrainerRepository personalTrainerRepository,
      StudentRepository studentRepository) {
    this.personalTrainerRepository = personalTrainerRepository;
    this.studentRepository = studentRepository;
  }

  public PersonalTrainer register(PersonalTrainer personalTrainer) {
    return personalTrainerRepository.save(personalTrainer);
  }

  public PersonalTrainer findById(Long id) {
    return personalTrainerRepository.findById(id)
        .orElseThrow(() -> new TrainerNotFoundException(id));
  }

  public PersonalTrainer findByUserId(UUID userId) {
    return personalTrainerRepository.findByUserId(userId)
        .orElseThrow(() -> new TrainerNotFoundException(userId));
  }

  public List<PersonalTrainer> findAll() {
    return personalTrainerRepository.findAll();
  }

  @Transactional
  public Student linkStudent(UUID trainerUserId, Long studentId) {
    PersonalTrainer trainer = findByUserId(trainerUserId);
    Student student = studentRepository.findById(studentId)
        .orElseThrow(() -> new StudentNotFoundException(studentId));
    student.setTrainer(trainer);
    return studentRepository.save(student);
  }

  @Transactional
  public Student unlinkStudent(UUID trainerUserId, Long studentId) {
    PersonalTrainer trainer = findByUserId(trainerUserId);
    Student student = studentRepository.findById(studentId)
        .orElseThrow(() -> new StudentNotFoundException(studentId));
    if (student.getTrainer() == null || !student.getTrainer().getId().equals(trainer.getId())) {
      throw new IllegalArgumentException("Student is not linked to this trainer.");
    }
    student.setTrainer(null);
    return studentRepository.save(student);
  }

  @Transactional
  public List<Student> findMyStudents(UUID trainerUserId) {
    PersonalTrainer trainer = findByUserId(trainerUserId);
    return studentRepository.findByTrainerId(trainer.getId());
  }
}