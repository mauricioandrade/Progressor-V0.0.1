package dev.mauriciodev.Progressor_V001.application.auth.trainer;

import dev.mauriciodev.Progressor_V001.domain.trainer.PersonalTrainer;
import dev.mauriciodev.Progressor_V001.infrastructure.persistence.PersonalTrainerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PersonalTrainerService {

  private final PersonalTrainerRepository personalTrainerRepository;

  public PersonalTrainerService(PersonalTrainerRepository personalTrainerRepository) {
    this.personalTrainerRepository = personalTrainerRepository;
  }

  public PersonalTrainer register(PersonalTrainer personalTrainer) {
    return personalTrainerRepository.save(personalTrainer);
  }

  public PersonalTrainer findById(Long id) {
    return personalTrainerRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("PersonalTrainer not found with id: " + id));
  }

  public List<PersonalTrainer> findAll() {
    return personalTrainerRepository.findAll();
  }
}
