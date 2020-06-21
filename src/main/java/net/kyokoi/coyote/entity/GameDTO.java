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
    private final int roomId;
    private final boolean isPlaying;
    private final List<Player> players;
    private final String message;
    private final int value;

    public GameDTO(Game game, UUID token) {
        this.roomId = game.getRoomId();
        this.isPlaying = (game.getGameState() == GameState.Playing);
        this.message = game.getMessage();
        this.value = game.getDeclareValue();
        if (game.getGameState() == GameState.Ready) {
            this.players = game.getUsers().stream()
            .map(user ->  new Player(user.getName(), "# No Card"))
            .collect(Collectors.toList());        
        } else if (game.getGameState()  == GameState.Playing) {
            this.players = game.getUsers().stream()
            .map(user ->  {
                if (token.equals(user.getToken()))
                    return new Player(user.getName(), "# You can't know");
                else
                    return new Player(user.getName(), user.printCardValue());
            })
            .collect(Collectors.toList());
        } else if (game.getGameState()  == GameState.Finish) {
            this.players = game.getUsers().stream()
            .map(user ->  new Player(user.getName(), user.printCardValue()))
            .collect(Collectors.toList()); 
        } else {
            this.players = null;
        }
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

