package dev.mauriciodev.progressor.infrastructure.persistence;

import dev.mauriciodev.progressor.domain.training.TrainingPlan;
import dev.mauriciodev.progressor.domain.shared.TrainingLevel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long> {

  List<TrainingPlan> findByLevel(TrainingLevel level);
}