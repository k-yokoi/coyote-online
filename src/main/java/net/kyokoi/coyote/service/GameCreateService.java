package net.kyokoi.coyote.service;

import net.kyokoi.coyote.entity.Game;
import net.kyokoi.coyote.entity.User;
import net.kyokoi.coyote.repository.GameRepository;
import net.kyokoi.coyote.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameCreateService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    public Game createGame(String name) {
        Game game = new Game();
        User user = new User(name, game.getRoomId(), true);
        game.addUser(user);
        gameRepository.save(game);
        userRepository.save(user);
        return game;
    }
}