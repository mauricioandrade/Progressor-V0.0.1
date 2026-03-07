package dev.mauriciodev.Progressor_V001.application.auth;

import dev.mauriciodev.Progressor_V001.domain.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(@NotBlank @Email String email,
                              @NotBlank @Size(min = 6) String password, @NotBlank String name,
                              String phone, @NotNull Role role) {

}

