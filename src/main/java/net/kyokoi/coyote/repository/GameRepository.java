package net.kyokoi.coyote.repository;
import net.kyokoi.coyote.entity.Game;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, Long> {
    public List<Game> findByRoomId(int roomId);

}