package co.buzmo.reflex.repo;

import co.buzmo.reflex.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByDeviceId(String deviceId);
}
