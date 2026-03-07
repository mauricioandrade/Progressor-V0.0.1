package dev.mauriciodev.Progressor_V001.application.auth.trainer;

public record TrainerRequest(
    String name,
    String email,
    String phone,
    String cref,
    String specialty
) {

}
