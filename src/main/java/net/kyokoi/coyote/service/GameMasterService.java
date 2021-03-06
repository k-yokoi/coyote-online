package net.kyokoi.coyote.service;

import net.kyokoi.coyote.entity.CardDeck;
import net.kyokoi.coyote.entity.Game;
import net.kyokoi.coyote.entity.User;
import net.kyokoi.coyote.repository.GameRepository;
import net.kyokoi.coyote.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameMasterService {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PushWebSocketService pushWebSocketService;

    public boolean startGame(int roomId, UUID token) {
        User user = userRepository.findByToken(token).get(0);
        if (user.getRoomId() != roomId || !user.isAdmin())
            return false;

        Game game = gameRepository.findByRoomId(roomId).get(0);
        try {
            game.startGame();
        } catch (Exception e) {
            e.printStackTrace();;
            return false;
        }
        gameRepository.saveAndFlush(game);
        pushWebSocketService.sendMessage(roomId, "start");
        return true;

    }
    
    public boolean restartGame(int roomId, UUID token){
        User user = userRepository.findByToken(token).get(0);
        if (user.getRoomId() != roomId || !user.isAdmin())
            return false;

        Game game = gameRepository.findByRoomId(roomId).get(0);
        game.restartGame();
        gameRepository.saveAndFlush(game);
        pushWebSocketService.sendMessage(roomId, "restart");
        return true;
    }
}
