package dev.mauriciodev.progressor.domain.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.mauriciodev.progressor.domain.person.Person;
import dev.mauriciodev.progressor.domain.trainer.PersonalTrainer;
import dev.mauriciodev.progressor.domain.training.TrainingPlan;
import dev.mauriciodev.progressor.domain.shared.Goal;
import dev.mauriciodev.progressor.domain.shared.TrainingLevel;
import dev.mauriciodev.progressor.domain.shared.Progressable;
import dev.mauriciodev.progressor.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student extends Person implements Progressable {

  @JsonIgnore
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", unique = true)
  private User user;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  private Double weight;
  private Double height;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Goal goal;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TrainingLevel trainingLevel;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "current_training_plan_id")
  private TrainingPlan currentTrainingPlan;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "student_training_history", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "training_plan_id"))
  private List<TrainingPlan> trainingHistory = new ArrayList<>();

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trainer_id")
  private PersonalTrainer trainer;

  @Column(name = "avatar_data", columnDefinition = "BYTEA")
  private byte[] avatarData;

  @Column(name = "avatar_content_type", length = 50)
  private String avatarContentType;

  protected Student() {
    super();
  }

  public Student(Long id, String name, String email, String phone, LocalDate birthDate,
      Double weight, Double height, Goal goal, TrainingLevel trainingLevel) {
    super(id, name, email, phone);
    updateBirthDate(birthDate);
    updateWeight(weight);
    updateHeight(height);
    this.goal = goal;
    this.trainingLevel = trainingLevel;
  }

  // --- GETTERS QUE ESTAVAM FALTANDO ---
  public PersonalTrainer getTrainer() {
    return trainer;
  }

  // --- MÉTODOS DE DOMÍNIO ---
  @Override
  public void evolve() {
    this.trainingLevel = switch (this.trainingLevel) {
      case BEGINNER -> TrainingLevel.INTERMEDIATE;
      case INTERMEDIATE -> TrainingLevel.ADVANCED;
      case ADVANCED -> throw new IllegalStateException("Student is already at the highest level");
    };
  }

  public Integer getAge() {
    if (this.birthDate == null) {
      return null;
    }
    return Period.between(this.birthDate, LocalDate.now()).getYears();
  }

  public void updateBirthDate(LocalDate birthDate) {
    if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Invalid birth date");
    }
    int calculatedAge = Period.between(birthDate, LocalDate.now()).getYears();
    if (calculatedAge < 10 || calculatedAge > 120) {
      throw new IllegalArgumentException("Student must be between 10 and 120 years old");
    }
    this.birthDate = birthDate;
  }

  public void updateWeight(Double weight) {
    if (weight == null || weight <= 0) {
      throw new IllegalArgumentException("Weight must be positive");
    }
    this.weight = weight;
  }

  public void updateHeight(Double height) {
    if (height == null || height <= 0) {
      throw new IllegalArgumentException("Height must be positive");
    }
    this.height = height;
  }

  public void assignTrainingPlan(TrainingPlan plan) {
    this.currentTrainingPlan = plan;
    if (plan != null && !trainingHistory.contains(plan)) {
      trainingHistory.add(plan);
    }
  }

  // --- OUTROS GETTERS E SETTERS ---
  public LocalDate getBirthDate() {
    return birthDate;
  }

  public Double getWeight() {
    return weight;
  }

  public Double getHeight() {
    return height;
  }

  public Goal getGoal() {
    return goal;
  }

  public void setGoal(Goal goal) {
    this.goal = goal;
  }

  public TrainingLevel getTrainingLevel() {
    return trainingLevel;
  }

  public TrainingPlan getCurrentTrainingPlan() {
    return currentTrainingPlan;
  }

  public List<TrainingPlan> getTrainingHistory() {
    return List.copyOf(trainingHistory);
  }

  public byte[] getAvatarData() {
    return avatarData;
  }

  public String getAvatarContentType() {
    return avatarContentType;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setTrainer(PersonalTrainer t) {
    this.trainer = t;
  }

  public void setAvatar(byte[] data, String contentType) {
    this.avatarData = data;
    this.avatarContentType = contentType;
  }

  @Override
  public String evaluateProgress() {
    return "Student " + getName() + " is level: " + trainingLevel;
  }
}