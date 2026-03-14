package dev.mauriciodev.progressor.application.student;

import dev.mauriciodev.progressor.application.nutrition.DietPlanResponse;
import dev.mauriciodev.progressor.application.training.TrainingPlanResponse;
import dev.mauriciodev.progressor.domain.shared.Goal;
import dev.mauriciodev.progressor.domain.shared.TrainingLevel;
import java.time.LocalDate;

public record StudentResponse(Long id, String name, String email, String phone, LocalDate birthDate,
                              Integer age, Double weight, Double height, Goal goal,
                              TrainingLevel trainingLevel, TrainingPlanResponse currentTrainingPlan,
                              DietPlanResponse currentDietPlan, Long nutritionistId,
                              byte[] avatarData, String avatarContentType) {

}