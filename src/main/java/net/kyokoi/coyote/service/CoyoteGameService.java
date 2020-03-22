package net.kyokoi.coyote.service;

import net.kyokoi.coyote.entity.Game;
import net.kyokoi.coyote.entity.User;
import net.kyokoi.coyote.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CoyoteGameService {
    @Autowired
    GameRepository gameRepository;

    public User coyote(UUID roomId, UUID token) {
        Game game = gameRepository.findByRoomId(roomId).get(0);
        if (!game.canRaiseOrCoyote(token))
            return null;
        User loosePlayer = game.coyote(token);
        gameRepository.save(game);
        return loosePlayer;
    }
}
