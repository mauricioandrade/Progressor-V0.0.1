package dev.mauriciodev.Progressor_V001.application.training;

import jakarta.validation.constraints.NotBlank;

public record ExerciseRequest(@NotBlank String name, String videoUrl) {

}