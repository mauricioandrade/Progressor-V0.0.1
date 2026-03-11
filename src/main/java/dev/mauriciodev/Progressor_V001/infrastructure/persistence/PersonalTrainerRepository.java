package dev.mauriciodev.Progressor_V001.infrastructure.persistence;

import dev.mauriciodev.Progressor_V001.domain.trainer.PersonalTrainer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalTrainerRepository extends JpaRepository<PersonalTrainer, Long> {

  Optional<PersonalTrainer> findByUserId(UUID userId);

  Optional<PersonalTrainer> findByCref(String cref);
}


