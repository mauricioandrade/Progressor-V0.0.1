package dev.mauriciodev.progressor.application.trainer;

public record TrainerResponse(
    Long id,
    String name,
    String email,
    String phone,
    String cref,
    String specialty
) {

}
