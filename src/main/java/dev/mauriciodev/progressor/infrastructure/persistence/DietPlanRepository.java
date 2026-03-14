package dev.mauriciodev.progressor.infrastructure.persistence;

import dev.mauriciodev.progressor.domain.nutrition.DietPlan;
import dev.mauriciodev.progressor.domain.shared.DietFocus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietPlanRepository extends JpaRepository<DietPlan, Long> {

  List<DietPlan> findByFocus(DietFocus focus);
}
