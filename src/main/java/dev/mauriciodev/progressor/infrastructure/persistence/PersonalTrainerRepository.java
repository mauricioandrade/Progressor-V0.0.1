package dev.mauriciodev.progressor.infrastructure.persistence;

import dev.mauriciodev.progressor.domain.trainer.PersonalTrainer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalTrainerRepository extends JpaRepository<PersonalTrainer, Long> {

  Optional<PersonalTrainer> findByUserId(UUID userId);

  Optional<PersonalTrainer> findByCref(String cref);
}


