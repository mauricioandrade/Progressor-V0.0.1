package dev.mauriciodev.progressor.application.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.mauriciodev.progressor.domain.shared.Goal;
import dev.mauriciodev.progressor.domain.shared.TrainingLevel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public record StudentRequest(@NotBlank(message = "Name is required") String name,

                             @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email,

                             String phone,

                             @NotNull(message = "Birth date is required") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate birthDate,

                             @Positive(message = "Weight must be positive") Double weight,

                             @Positive(message = "Height must be positive") Double height,

                             Goal goal,

                             TrainingLevel trainingLevel) {

}