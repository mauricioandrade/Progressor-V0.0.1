package dev.mauriciodev.progressor.application.student;

import dev.mauriciodev.progressor.domain.student.Student;
import dev.mauriciodev.progressor.domain.student.StudentNotFoundException;
import dev.mauriciodev.progressor.domain.training.TrainingPlan;
import dev.mauriciodev.progressor.infrastructure.persistence.StudentRepository;
import dev.mauriciodev.progressor.infrastructure.persistence.TrainingPlanRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StudentService {

  private static final long MAX_SIZE_BYTES = 5 * 1024 * 1024;
  private static final List<String> ALLOWED_TYPES = List.of(MediaType.IMAGE_JPEG_VALUE,
      MediaType.IMAGE_PNG_VALUE, "image/webp");

  private final StudentRepository studentRepository;
  private final TrainingPlanRepository trainingPlanRepository;

  public StudentService(StudentRepository studentRepository,
      TrainingPlanRepository trainingPlanRepository) {
    this.studentRepository = studentRepository;
    this.trainingPlanRepository = trainingPlanRepository;
  }

  @Transactional
  public Student register(Student student) {
    return studentRepository.save(student);
  }

  @Transactional
  public Student findByUserId(UUID userId) {
    return studentRepository.findByUserId(userId)
        .orElseThrow(() -> new StudentNotFoundException(userId));
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
    student.evolve();

    List<TrainingPlan> plansForNewLevel = trainingPlanRepository.findByLevel(
        student.getTrainingLevel());

    if (!plansForNewLevel.isEmpty()) {
      student.assignTrainingPlan(plansForNewLevel.get(0));
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
    if (request.birthDate() != null) {
      student.updateBirthDate(request.birthDate());
    }
    if (request.weight() != null) {
      student.updateWeight(request.weight());
    }
    if (request.height() != null) {
      student.updateHeight(request.height());
    }
    if (request.goal() != null) {
      student.setGoal(request.goal());
    }

    return studentRepository.save(student);
  }

  @Transactional
  public void uploadAvatar(UUID userId, MultipartFile file) throws IOException {
    validateAvatar(file);
    Student student = findByUserId(userId);
    student.setAvatar(file.getBytes(), file.getContentType());
    studentRepository.save(student);
  }

  private void validateAvatar(MultipartFile file) {
    if (file.isEmpty()) {
      throw new IllegalArgumentException("File must not be empty.");
    }
    if (file.getSize() > MAX_SIZE_BYTES) {
      throw new IllegalArgumentException("File exceeds 5MB.");
    }

    String contentType = file.getContentType();
    if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
      throw new IllegalArgumentException("Only JPEG, PNG and WebP are accepted.");
    }
  }

  @Transactional
  public Student findAvatarByUserId(UUID userId) {
    return findByUserId(userId);
  }
}