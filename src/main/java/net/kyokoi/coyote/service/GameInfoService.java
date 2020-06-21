package net.kyokoi.coyote.service;

import net.kyokoi.coyote.entity.Game;
import net.kyokoi.coyote.entity.GameDTO;
import net.kyokoi.coyote.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameInfoService {
    @Autowired
    GameRepository gameRepository;

    public GameDTO getGameInfo(int roomId, UUID token) {
        List<Game> results = gameRepository.findByRoomId(roomId);
        if (results.size()==0) return null;

        return new GameDTO(results.get(0), token);
    }

    public Integer getVersion(int roomId) {
        List<Game> results = gameRepository.findByRoomId(roomId);
        if (results.size()==0) return null;

        return results.get(0).getVersion();
    }
}
