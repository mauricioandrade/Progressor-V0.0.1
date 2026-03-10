package dev.mauriciodev.Progressor_V001.domain.student;

import dev.mauriciodev.Progressor_V001.domain.person.Person;
import dev.mauriciodev.Progressor_V001.domain.trainer.PersonalTrainer;
import dev.mauriciodev.Progressor_V001.domain.training.TrainingPlan;
import dev.mauriciodev.Progressor_V001.domain.shared.Goal;
import dev.mauriciodev.Progressor_V001.domain.shared.TrainingLevel;
import dev.mauriciodev.Progressor_V001.domain.shared.Progressable;
import dev.mauriciodev.Progressor_V001.domain.user.User;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student extends Person implements Progressable {

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", unique = true)
  private User user;

  private Integer age;

  private Double weight;

  private Double height;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Goal goal;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TrainingLevel trainingLevel;

  @ManyToOne
  @JoinColumn(name = "current_training_plan_id")
  private TrainingPlan currentTrainingPlan;

  @ManyToMany
  @JoinTable(name = "student_training_history", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "training_plan_id"))
  private List<TrainingPlan> trainingHistory = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trainer_id")
  private PersonalTrainer trainer;

  @Column(name = "avatar_data", columnDefinition = "BYTEA")
  private byte[] avatarData;

  @Column(name = "avatar_content_type", length = 50)
  private String avatarContentType;

  protected Student() {
    super(null, null, null, null);
  }

  public Student(Long id, String name, String email, String phone, Integer age, Double weight,
      Double height, Goal goal, TrainingLevel trainingLevel) {
    super(id, name, email, phone);
    this.age = age;
    this.weight = weight;
    this.height = height;
    this.goal = goal;
    this.trainingLevel = trainingLevel;
  }

  @Override
  public void evolve() {
    this.trainingLevel = switch (this.trainingLevel) {
      case BEGINNER -> TrainingLevel.INTERMEDIATE;
      case INTERMEDIATE -> TrainingLevel.ADVANCED;
      case ADVANCED -> throw new IllegalStateException("Student is already at the highest level");
    };
  }

  @Override
  public String evaluateProgress() {
    return "Student " + getName() + " is currently at level: " + trainingLevel;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Double getWeight() {
    return weight;
  }

  public void setWeight(Double weight) {
    this.weight = weight;
  }

  public Double getHeight() {
    return height;
  }

  public void setHeight(Double height) {
    this.height = height;
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

  public PersonalTrainer getTrainer() {
    return trainer;
  }

  public void setTrainer(PersonalTrainer t) {
    this.trainer = t;
  }

  public TrainingPlan getCurrentTrainingPlan() {
    return currentTrainingPlan;
  }

  public void setCurrentTrainingPlan(TrainingPlan plan) {
    this.currentTrainingPlan = plan;
  }

  public List<TrainingPlan> getTrainingHistory() {
    return trainingHistory;
  }

  public void addToHistory(TrainingPlan plan) {
    this.trainingHistory.add(plan);
  }

  public byte[] getAvatarData() {
    return avatarData;
  }

  public void setAvatarData(byte[] avatarData) {
    this.avatarData = avatarData;
  }

  public String getAvatarContentType() {
    return avatarContentType;
  }

  public void setAvatarContentType(String avatarContentType) {
    this.avatarContentType = avatarContentType;
  }
}