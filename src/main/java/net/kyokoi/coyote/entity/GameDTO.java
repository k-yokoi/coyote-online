package net.kyokoi.coyote.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class GameDTO {
    private final UUID roomId;
    private final boolean isPlaying;
    private final List<Player> players;
    private final String message;

    public GameDTO(Game game, UUID token) {
        this.roomId = game.getRoomId();
        this.isPlaying = (game.getGameState() == GameState.Playing);
        this.players = game.getUsers().stream()
                .map(user ->  {
                    if (user.getCard()==null) {
                        return new Player(user.getName(), user.printCardValue());
                    } else {
                        if (token.equals(user.getToken()))
                            return new Player(user.getName(), "# You can't know");
                        else
                            return new Player(user.getName(), user.printCardValue());
                    }
                })
                .collect(Collectors.toList());
        this.message = game.getMessage();
    }

    @Getter
    class Player {
        private String name;
        private String card;

        Player(String name, String card) {
            this.name = name;
            this.card = card;
        }
    }
}

