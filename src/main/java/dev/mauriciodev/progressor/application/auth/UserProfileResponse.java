package dev.mauriciodev.progressor.application.auth;

import dev.mauriciodev.progressor.domain.user.Role;
import java.util.UUID;

public record UserProfileResponse(UUID id, String email, Role role) {

}