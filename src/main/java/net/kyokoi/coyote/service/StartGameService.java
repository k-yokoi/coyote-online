package net.kyokoi.coyote.service;

import net.kyokoi.coyote.entity.Cards;
import net.kyokoi.coyote.entity.Game;
import net.kyokoi.coyote.entity.User;
import net.kyokoi.coyote.repository.GameRepository;
import net.kyokoi.coyote.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StartGameService {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    public boolean startGame(UUID roomId, UUID token) {
        User user = userRepository.findByToken(token).get(0);
        if (!user.getRoomId().equals(roomId) || !user.isAdmin())
            return false;

        Game game = gameRepository.findByRoomId(roomId).get(0);
        game.startGame();
        gameRepository.saveAndFlush(game);

        var cards = new Cards();
        game.getUsers().forEach(player -> player.setCardValue(cards.drawCard().getValue()));
        gameRepository.saveAndFlush(game);
        return true;

    }
}
