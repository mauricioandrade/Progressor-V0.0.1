package dev.mauriciodev.Progressor_V001.application.auth;

import dev.mauriciodev.Progressor_V001.domain.user.Role;
import java.util.UUID;

public record UserProfileResponse(UUID id, String email, Role role) {

}