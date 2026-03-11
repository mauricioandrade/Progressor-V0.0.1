package dev.mauriciodev.Progressor_V001.application.student;

import dev.mauriciodev.Progressor_V001.application.training.TrainingPlanMapper;
import dev.mauriciodev.Progressor_V001.domain.student.Student;

public final class StudentMapper {

  private StudentMapper() {
  }

  public static StudentResponse toResponse(Student student) {
    if (student == null) {
      return null;
    }
    return new StudentResponse(student.getId(), student.getName(), student.getEmail(),
        student.getPhone(), student.getAge(), student.getWeight(), student.getHeight(),
        student.getGoal(), student.getTrainingLevel(),
        student.getCurrentTrainingPlan() != null ? TrainingPlanMapper.toResponse(
            student.getCurrentTrainingPlan()) : null, student.getAvatarData(),
        student.getAvatarContentType());
  }
}
