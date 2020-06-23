package net.kyokoi.coyote.service;

import net.kyokoi.coyote.entity.Game;
import net.kyokoi.coyote.entity.User;
import net.kyokoi.coyote.repository.GameRepository;
import net.kyokoi.coyote.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.UUID;

@Service
public class GameCreateService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PushWebSocketService pushWebSocketService;

    public Game createGame(String name) {
        if (name.equals("")) return null;
        Game game = new Game();
        User user = new User(HtmlUtils.htmlEscape(name), game.getRoomId(), true);
        game.addUser(user);
        gameRepository.save(game);
        userRepository.save(user);
        return game;
    }
}