package dev.mauriciodev.progressor.application.student;

import dev.mauriciodev.progressor.application.training.TrainingPlanResponse;
import dev.mauriciodev.progressor.domain.shared.Goal;
import dev.mauriciodev.progressor.domain.shared.TrainingLevel;

public record StudentResponse(Long id, String name, String email, String phone, Integer age,
                              Double weight, Double height, Goal goal, TrainingLevel trainingLevel,
                              TrainingPlanResponse currentTrainingPlan, byte[] avatarData,
                              String avatarContentType) {

}