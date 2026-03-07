package dev.mauriciodev.Progressor_V001.infrastructure.persistence;

import dev.mauriciodev.Progressor_V001.domain.training.TrainingPlan;
import dev.mauriciodev.Progressor_V001.domain.shared.TrainingLevel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long> {

  List<TrainingPlan> findByLevel(TrainingLevel level);
}