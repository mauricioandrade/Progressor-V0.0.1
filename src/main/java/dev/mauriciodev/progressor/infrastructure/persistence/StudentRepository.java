package dev.mauriciodev.progressor.infrastructure.persistence;

import dev.mauriciodev.progressor.domain.student.Student;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

  Optional<Student> findByUserId(UUID userId);

  List<Student> findByTrainerId(Long trainerId);

  List<Student> findByCurrentTrainingPlanId(Long trainingPlanId);
}

