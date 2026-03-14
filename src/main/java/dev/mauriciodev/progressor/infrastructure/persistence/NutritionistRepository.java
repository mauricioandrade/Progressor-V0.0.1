package dev.mauriciodev.progressor.infrastructure.persistence;

import dev.mauriciodev.progressor.domain.nutritionist.Nutritionist;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionistRepository extends JpaRepository<Nutritionist, Long> {

  Optional<Nutritionist> findByUserId(UUID userId);

  Optional<Nutritionist> findByCrn(String crn);
}
