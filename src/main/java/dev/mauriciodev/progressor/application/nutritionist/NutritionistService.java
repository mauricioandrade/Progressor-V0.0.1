package dev.mauriciodev.progressor.application.nutritionist;

import dev.mauriciodev.progressor.domain.nutritionist.Nutritionist;
import dev.mauriciodev.progressor.domain.nutritionist.NutritionistNotFoundException;
import dev.mauriciodev.progressor.domain.student.Student;
import dev.mauriciodev.progressor.domain.student.StudentNotFoundException;
import dev.mauriciodev.progressor.infrastructure.persistence.NutritionistRepository;
import dev.mauriciodev.progressor.infrastructure.persistence.StudentRepository;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NutritionistService {

  private static final long MAX_SIZE_BYTES = 5 * 1024 * 1024;
  private static final List<String> ALLOWED_TYPES = List.of(MediaType.IMAGE_JPEG_VALUE,
      MediaType.IMAGE_PNG_VALUE, "image/webp");

  private final NutritionistRepository nutritionistRepository;
  private final StudentRepository studentRepository;

  public NutritionistService(NutritionistRepository nutritionistRepository,
      StudentRepository studentRepository) {
    this.nutritionistRepository = nutritionistRepository;
    this.studentRepository = studentRepository;
  }

  public Nutritionist findById(Long id) {
    return nutritionistRepository.findById(id)
        .orElseThrow(() -> new NutritionistNotFoundException(id));
  }

  public Nutritionist findByUserId(UUID userId) {
    return nutritionistRepository.findByUserId(userId)
        .orElseThrow(() -> new NutritionistNotFoundException(userId));
  }

  public List<Nutritionist> findAll() {
    return nutritionistRepository.findAll();
  }

  public List<Student> findMyStudents(UUID nutritionistUserId) {
    Nutritionist nutritionist = findByUserId(nutritionistUserId);
    return studentRepository.findByNutritionistId(nutritionist.getId());
  }

  @Transactional
  public Student linkStudent(UUID nutritionistUserId, Long studentId) {
    Nutritionist nutritionist = findByUserId(nutritionistUserId);
    Student student = studentRepository.findById(studentId)
        .orElseThrow(() -> new StudentNotFoundException(studentId));

    student.setNutritionist(nutritionist);
    return studentRepository.save(student);
  }

  @Transactional
  public Student unlinkStudent(UUID nutritionistUserId, Long studentId) {
    Nutritionist nutritionist = findByUserId(nutritionistUserId);
    Student student = studentRepository.findById(studentId)
        .orElseThrow(() -> new StudentNotFoundException(studentId));

    if (student.getNutritionist() == null || !student.getNutritionist().getId()
        .equals(nutritionist.getId())) {
      throw new IllegalArgumentException("Student is not linked to this nutritionist.");
    }

    student.setNutritionist(null);
    return studentRepository.save(student);
  }

  @Transactional
  public void uploadAvatar(UUID userId, MultipartFile file) throws IOException {
    validateAvatar(file);
    Nutritionist nutritionist = findByUserId(userId);
    nutritionist.setAvatar(file.getBytes(), file.getContentType());
    nutritionistRepository.save(nutritionist);
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