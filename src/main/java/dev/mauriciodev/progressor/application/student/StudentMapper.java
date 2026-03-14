package dev.mauriciodev.progressor.application.student;

import dev.mauriciodev.progressor.application.nutrition.DietPlanMapper;
import dev.mauriciodev.progressor.application.training.TrainingPlanMapper;
import dev.mauriciodev.progressor.domain.student.Student;

public final class StudentMapper {

  private StudentMapper() {
  }

  public static StudentResponse toResponse(Student student) {
    if (student == null) {
      return null;
    }

    return new StudentResponse(student.getId(), student.getName(), student.getEmail(),
        student.getPhone(), student.getBirthDate(), student.getAge(), student.getWeight(),
        student.getHeight(), student.getGoal(), student.getTrainingLevel(),
        student.getCurrentTrainingPlan() != null ? TrainingPlanMapper.toResponse(
            student.getCurrentTrainingPlan()) : null,
        student.getCurrentDietPlan() != null ? DietPlanMapper.toResponse(
            student.getCurrentDietPlan()) : null,
        student.getNutritionist() != null ? student.getNutritionist().getId() : null,
        student.getAvatarData(), student.getAvatarContentType());
  }
}