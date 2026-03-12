package dev.mauriciodev.progressor.presentation.exception;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, int status, String message, String path) {

}