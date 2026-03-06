package dev.mauriciodev.Progressor_V001.infrastructure.persistence;

import dev.mauriciodev.Progressor_V001.domain.user.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByEmail(String email);
}
