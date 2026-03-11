package dev.mauriciodev.Progressor_V001.application.auth;

import dev.mauriciodev.Progressor_V001.domain.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email,

    @NotBlank(message = "Password is required") @Size(min = 8, message = "Password must be at least 8 characters long") @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one uppercase letter and one number") String password,

    @NotBlank(message = "Name is required") String name,

    String phone,

    @NotNull(message = "Role is required") Role role) {

}