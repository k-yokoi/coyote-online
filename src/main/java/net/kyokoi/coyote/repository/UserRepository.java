package net.kyokoi.coyote.repository;

import net.kyokoi.coyote.entity.Game;
import net.kyokoi.coyote.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    public List<User> findByToken(UUID token);
}
