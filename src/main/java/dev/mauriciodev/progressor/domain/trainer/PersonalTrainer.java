package dev.mauriciodev.progressor.domain.trainer;

import dev.mauriciodev.progressor.domain.person.Person;
import dev.mauriciodev.progressor.domain.user.User;
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

  @Column(nullable = false, unique = true)
  private String cref;

  private String specialty;

  @Column(name = "avatar_data", columnDefinition = "BYTEA")
  private byte[] avatarData;

  @Column(name = "avatar_content_type", length = 50)
  private String avatarContentType;

  protected PersonalTrainer() {
    super();
  }

  public PersonalTrainer(Long id, String name, String email, String phone, String cref,
      String specialty) {
    super(id, name, email, phone);
    if (cref == null || cref.isBlank()) {
      throw new IllegalArgumentException("CREF must not be blank");
    }
    this.cref = cref;
    this.specialty = specialty;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setAvatar(byte[] data, String contentType) {
    this.avatarData = data;
    this.avatarContentType = contentType;
  }

  public void updateProfile(String name, String phone, String specialty) {
    if (name != null) {
      setName(name);
    }
    if (phone != null) {
      setPhone(phone);
    }
    this.specialty = specialty;
  }

  public User getUser() {
    return user;
  }

  public String getCref() {
    return cref;
  }

  public String getSpecialty() {
    return specialty;
  }

  public byte[] getAvatarData() {
    return avatarData;
  }

  public String getAvatarContentType() {
    return avatarContentType;
  }
}
