package dev.mauriciodev.Progressor_V001.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "personal_trainers")
public class PersonalTrainer extends Person {

  @Column(unique = true, nullable = false)
  private String cref;

  @Column(nullable = false)
  private String specialty;

  public PersonalTrainer(Long id, String name, String email, String phone,
      String cref, String specialty) {
    super(id, name, email, phone);
    this.cref = cref;
    this.specialty = specialty;
  }

  public String getCref() {
    return cref;
  }

  public String getSpecialty() {
    return specialty;
  }
}
