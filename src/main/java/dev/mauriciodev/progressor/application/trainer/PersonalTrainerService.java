package dev.mauriciodev.progressor.application.trainer;

import dev.mauriciodev.progressor.domain.student.Student;
import dev.mauriciodev.progressor.domain.student.StudentNotFoundException;
import dev.mauriciodev.progressor.domain.trainer.PersonalTrainer;
import dev.mauriciodev.progressor.domain.trainer.TrainerNotFoundException;
import dev.mauriciodev.progressor.infrastructure.persistence.PersonalTrainerRepository;
import dev.mauriciodev.progressor.infrastructure.persistence.StudentRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PersonalTrainerService {

  private static final long MAX_SIZE_BYTES = 5 * 1024 * 1024;
  private static final List<String> ALLOWED_TYPES = List.of(MediaType.IMAGE_JPEG_VALUE,
      MediaType.IMAGE_PNG_VALUE, "image/webp");

  private final PersonalTrainerRepository personalTrainerRepository;
  private final StudentRepository studentRepository;

  public PersonalTrainerService(PersonalTrainerRepository personalTrainerRepository,
      StudentRepository studentRepository) {
    this.personalTrainerRepository = personalTrainerRepository;
    this.studentRepository = studentRepository;
  }

  @Transactional
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

  public List<Student> findMyStudents(UUID trainerUserId) {
    PersonalTrainer trainer = findByUserId(trainerUserId);
    return studentRepository.findByTrainerId(trainer.getId());
  }

  @Transactional
  public void uploadAvatar(UUID userId, MultipartFile file) throws IOException {
    validateAvatar(file);
    PersonalTrainer trainer = findByUserId(userId);

    trainer.setAvatarData(file.getBytes());
    trainer.setAvatarContentType(file.getContentType());
    personalTrainerRepository.save(trainer);
  }

  private void validateAvatar(MultipartFile file) {
    if (file.isEmpty()) {
      throw new IllegalArgumentException("File must not be empty.");
    }
    if (file.getSize() > MAX_SIZE_BYTES) {
      throw new IllegalArgumentException("File exceeds the 5MB limit.");
    }
    String contentType = file.getContentType();
    if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
      throw new IllegalArgumentException("Only JPEG, PNG and WebP images are accepted.");
    }
  }
}