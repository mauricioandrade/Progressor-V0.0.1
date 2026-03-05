package dev.mauriciodev.Progressor_V001.dto.response;

public record TrainerResponse(
    Long id,
    String name,
    String email,
    String phone,
    String cref,
    String specialty
) {

}
