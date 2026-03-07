package dev.mauriciodev.Progressor_V001.infrastructure.persistence;

import dev.mauriciodev.Progressor_V001.domain.trainer.PersonalTrainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalTrainerRepository extends JpaRepository<PersonalTrainer, Long> {

}
