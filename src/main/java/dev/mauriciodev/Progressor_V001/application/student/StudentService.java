package dev.mauriciodev.Progressor_V001.application.student;

import dev.mauriciodev.Progressor_V001.domain.student.Student;
import dev.mauriciodev.Progressor_V001.domain.student.StudentNotFoundException;
import dev.mauriciodev.Progressor_V001.domain.training.TrainingPlan;
import dev.mauriciodev.Progressor_V001.domain.shared.TrainingLevel;
import dev.mauriciodev.Progressor_V001.infrastructure.persistence.StudentRepository;
import dev.mauriciodev.Progressor_V001.infrastructure.persistence.TrainingPlanRepository;
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

  @Transactional
  public void uploadAvatar(UUID userId, MultipartFile file) throws IOException {
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
    Student student = findByUserId(userId);
    student.setAvatarData(file.getBytes());
    student.setAvatarContentType(contentType);
    studentRepository.save(student);
  }

  public Student findAvatarByUserId(UUID userId) {
    return findByUserId(userId);
  }
}
