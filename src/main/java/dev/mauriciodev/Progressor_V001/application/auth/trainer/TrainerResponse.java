package dev.mauriciodev.Progressor_V001.application.auth.trainer;

public record TrainerResponse(
    Long id,
    String name,
    String email,
    String phone,
    String cref,
    String specialty
) {

}
