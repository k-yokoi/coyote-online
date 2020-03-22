package net.kyokoi.coyote.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
public class Game {
    private @Id @GeneratedValue Long id;
    private final UUID roomId;
    private boolean isPlaying;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<User> users;
    private String message;
    private int declareValue;
    private int turnIndex;
    @Transient
    public static final int MAX_USERS = 12;

    public Game() {
        this.roomId = UUID.randomUUID();
        this.isPlaying = false;
        this.users = new ArrayList<>();
        this.message = "Please wait for host user to start.";
    }

    @Transient
    public boolean canJoin() {
        if (isPlaying) return false;
        if (users.size()>=MAX_USERS) return false;
        return true;
    }

    @Transient
    public boolean addUser(User user) {
        if (!canJoin()) return false;
        return users.add(user);
    }

    public void startGame() {
        if (!isPlaying)
            isPlaying = true;
        declareValue = 0;
        turnIndex = 0;
        message = "Game Start! Next, " + users.get(0).getName() + "'s turn. Please raise or coyote.";
    }


    public boolean canRaiseOrCoyote(UUID token) {
        if (!isPlaying)
            return false;
        return token.equals(users.get(turnIndex).getToken());
    }

    public boolean raise(int value, UUID token) {
        if (!canRaiseOrCoyote(token))
            return false;
        if (value <= declareValue)
            return false;
        this.declareValue = value;
        message = users.get(turnIndex).getName() + " raised " + Integer.toString(value) + ". ";
        turnIndex = getNextTurnIndex(turnIndex);
        message += "Next, " + users.get(turnIndex).getName() + "'s turn. Please raise or coyote.";
        return true;
    }

    private int getNextTurnIndex(int turnIndex) {
        if (turnIndex + 1 >= users.size() )
            return 0;
        return turnIndex + 1;
    }
    private int getPrevTurnIndex(int turnIndex) {
        if (turnIndex - 1 < 0)
            return users.size() - 1;
        return turnIndex - 1;
    }

    public User coyote(UUID token) {
        if (!canRaiseOrCoyote(token))
            return null;
        int sumValue = 0;
        for (var user: users)
            sumValue += user.getCardValue();
        message = users.get(turnIndex).getName() + " call Coyote! ";

        if (sumValue < declareValue) {
            message += users.get(getPrevTurnIndex(turnIndex)).getName() + " loses... ("
            + users.get(getPrevTurnIndex(turnIndex)).getName() + " raised " + Integer.toString(declareValue) + ". "
                    + "Total is " + Integer.toString(sumValue) + ". )";

            return users.get(getPrevTurnIndex(turnIndex));
        } else {
            message += users.get(turnIndex).getName() + " loses... ("
                    + users.get(getPrevTurnIndex(turnIndex)).getName() + " raised " + Integer.toString(declareValue) + ". "
                    + "Total is " + Integer.toString(sumValue) + ". )";
            return users.get(turnIndex);
        }

    }
}
