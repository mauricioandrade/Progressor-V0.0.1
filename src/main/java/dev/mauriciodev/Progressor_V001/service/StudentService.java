package dev.mauriciodev.Progressor_V001.service;

import dev.mauriciodev.Progressor_V001.domain.entity.Student;
import dev.mauriciodev.Progressor_V001.repository.StudentRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

  private final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
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
}
