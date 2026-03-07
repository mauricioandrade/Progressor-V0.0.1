package dev.mauriciodev.Progressor_V001.domain.trainer;

import dev.mauriciodev.Progressor_V001.domain.person.Person;
import dev.mauriciodev.Progressor_V001.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "personal_trainers")
public class PersonalTrainer extends Person {

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", unique = true)
  private User user;

  @Column(unique = true)
  private String cref;

  private String specialty;

  protected PersonalTrainer() {
    super(null, null, null, null);
  }

  public PersonalTrainer(Long id, String name, String email, String phone, String cref,
      String specialty) {
    super(id, name, email, phone);
    this.cref = cref;
    this.specialty = specialty;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getCref() {
    return cref;
  }

  public String getSpecialty() {
    return specialty;
  }
}