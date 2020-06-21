package net.kyokoi.coyote.service;

import net.kyokoi.coyote.entity.Game;
import net.kyokoi.coyote.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RaiseGameService {
    @Autowired
    GameRepository gameRepository;

    public boolean raise(int roomId, int value, UUID token) {
        Game game = gameRepository.findByRoomId(roomId).get(0);
        if (!game.canRaiseOrCoyote(token))
            return false;
        game.raise(value, token);
        game.incrementVersion();
        gameRepository.save(game);

        return true;
    }
}
