package dev.mauriciodev.progressor.application.nutrition;

import dev.mauriciodev.progressor.domain.nutrition.DietPlan;
import dev.mauriciodev.progressor.domain.nutrition.DietPlanNotFoundException;
import dev.mauriciodev.progressor.domain.nutrition.Meal;
import dev.mauriciodev.progressor.domain.student.Student;
import dev.mauriciodev.progressor.domain.student.StudentNotFoundException;
import dev.mauriciodev.progressor.infrastructure.persistence.DietPlanRepository;
import dev.mauriciodev.progressor.infrastructure.persistence.StudentRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DietPlanService {

  private final DietPlanRepository dietPlanRepository;
  private final StudentRepository studentRepository;

  public DietPlanService(DietPlanRepository dietPlanRepository,
      StudentRepository studentRepository) {
    this.dietPlanRepository = dietPlanRepository;
    this.studentRepository = studentRepository;
  }

  public DietPlan findById(Long id) {
    return dietPlanRepository.findById(id).orElseThrow(() -> new DietPlanNotFoundException(id));
  }

  public List<DietPlan> findAll() {
    return dietPlanRepository.findAll();
  }

  @Transactional
  public DietPlan createForStudent(UUID nutritionistUserId, DietPlanRequest request) {
    Student student = studentRepository.findById(request.studentId())
        .orElseThrow(() -> new StudentNotFoundException(request.studentId()));

    List<Meal> meals = toMeals(request.meals());

    DietPlan plan = new DietPlan(null, request.name(), request.durationWeeks(), request.focus(),
        request.dailyCalories(), meals);
    DietPlan saved = dietPlanRepository.save(plan);

    student.assignDietPlan(saved);
    studentRepository.save(student);

    return saved;
  }

  @Transactional
  public DietPlan update(Long id, DietPlanUpdateRequest request) {
    DietPlan plan = findById(id);
    List<Meal> newMeals = toMeals(request.meals());
    plan.update(request.name(), request.durationWeeks(), request.focus(), request.dailyCalories(),
        newMeals);
    return dietPlanRepository.save(plan);
  }

  @Transactional
  public Student assignToStudent(Long planId, Long studentId) {
    DietPlan plan = findById(planId);
    Student student = studentRepository.findById(studentId)
        .orElseThrow(() -> new StudentNotFoundException(studentId));

    student.assignDietPlan(plan);
    return studentRepository.save(student);
  }

  @Transactional
  public void delete(Long id) {
    DietPlan plan = findById(id);
    List<Student> affected = studentRepository.findByCurrentDietPlanId(id);

    for (Student student : affected) {
      student.assignDietPlan(null);
      studentRepository.save(student);
    }

    dietPlanRepository.delete(plan);
  }

  public DietPlan findCurrentForStudentById(Long studentId) {
    Student student = studentRepository.findById(studentId)
        .orElseThrow(() -> new StudentNotFoundException(studentId));
    if (student.getCurrentDietPlan() == null) {
      throw new DietPlanNotFoundException("Student has no active diet plan");
    }
    return student.getCurrentDietPlan();
  }

  public DietPlan findCurrentForStudent(UUID userId) {
    Student student = studentRepository.findByUserId(userId)
        .orElseThrow(() -> new StudentNotFoundException(userId));
    if (student.getCurrentDietPlan() == null) {
      throw new DietPlanNotFoundException("Student has no active diet plan");
    }
    return student.getCurrentDietPlan();
  }

  private List<Meal> toMeals(List<MealRequest> requests) {
    return requests.stream().map(m -> new Meal(m.name(), m.description(), m.calories(), m.time()))
        .toList();
  }
}