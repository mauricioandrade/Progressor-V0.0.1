package dev.mauriciodev.Progressor_V001.application.student;

import dev.mauriciodev.Progressor_V001.domain.student.Student;

public final class StudentMapper {

  private StudentMapper() {
  }

  public static StudentResponse toResponse(Student student) {
    return new StudentResponse(student.getId(), student.getName(), student.getEmail(),
        student.getPhone(), student.getAge(), student.getWeight(), student.getHeight(),
        student.getGoal(), student.getTrainingLevel(),
        student.getCurrentTrainingPlan() != null ? student.getCurrentTrainingPlan().getName()
            : null);
  }
}