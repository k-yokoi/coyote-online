package net.kyokoi.coyote.service;

import net.kyokoi.coyote.entity.Game;
import net.kyokoi.coyote.entity.User;
import net.kyokoi.coyote.repository.GameRepository;
import net.kyokoi.coyote.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JoinGameService {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    public UUID joinGame(int roomId, String name) {
        Game game = gameRepository.findByRoomId(roomId).get(0);
        if (!game.canJoin()) return null;
        if (name.equals("")) return null;

        User user = new User(name, game.getRoomId());
        userRepository.save(user);
        game.addUser(user);
        game.incrementVersion();
        gameRepository.save(game);
        return user.getToken();
    }


}
