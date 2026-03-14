package dev.mauriciodev.progressor.domain.person;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(unique = true, nullable = false)
  private String email;

  private String phone;

  protected Person() {
  }

  public Person(Long id, String name, String email, String phone) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name must not be blank");
    }
    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("Email must not be blank");
    }
    this.id = id;
    this.name = name;
    this.email = email;
    this.phone = phone;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name must not be blank");
    }
    this.name = name;
  }

  public void setPhone(String phone) {
    if (phone != null && phone.isBlank()) {
      throw new IllegalArgumentException("Phone must not be blank when provided");
    }
    this.phone = phone;
  }
}