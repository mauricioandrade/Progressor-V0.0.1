package dev.mauriciodev.progressor.application.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.mauriciodev.progressor.domain.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public record RegisterRequest(
    @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email,

    @NotBlank(message = "Password is required") @Size(min = 8, message = "Password must be at least 8 characters long") @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one uppercase letter and one number") String password,

    @NotBlank(message = "Name is required") String name,

    String phone,

    String cref,

    String crn,

    @NotNull(message = "Role is required") Role role,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate birthDate) {

}