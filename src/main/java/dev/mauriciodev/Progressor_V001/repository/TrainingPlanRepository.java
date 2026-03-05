package dev.mauriciodev.Progressor_V001.repository;

import dev.mauriciodev.Progressor_V001.domain.entity.TrainingPlan;
import dev.mauriciodev.Progressor_V001.domain.enums.TrainingLevel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long> {

  List<TrainingPlan> findByLevel(TrainingLevel level);
}