package dev.mauriciodev.progressor.application.trainer;

import jakarta.validation.constraints.NotBlank;

public record TrainerRequest(
    @NotBlank String name,
    @NotBlank String email,
    String phone,
    @NotBlank String cref,
    @NotBlank String specialty
) {

}