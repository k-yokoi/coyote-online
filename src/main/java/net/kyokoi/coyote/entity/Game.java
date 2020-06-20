package net.kyokoi.coyote.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Entity
public class Game {
    private @Id @GeneratedValue Long id;
    private final int roomId;
    private GameState gameState;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<User> users;
    private String message;
    private int declareValue;
    private int turnIndex;
    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    private CardDeck cardDeck;
    @Transient
    public static final int MAX_USERS = 12;

    public Game() {
        Random random =  new Random(System.currentTimeMillis());
        this.roomId = random.nextInt(900000) + 100000;
        this.gameState = GameState.Ready;
        this.users = new ArrayList<>();
        this.message = "Please wait for host user to start.";
        this.cardDeck = new CardDeck();
        this.cardDeck.init();
    }

    @Transient
    public boolean canJoin() {
        if (gameState != GameState.Ready) return false;
        if (users.size()>=MAX_USERS) return false;
        return true;
    }

    @Transient
    public boolean addUser(User user) {
        if (!canJoin()) return false;
        return users.add(user);
    }

    public void startGame() throws Exception {
        if (gameState != GameState.Ready && gameState != GameState.Finish)
            throw new Exception();
        
        if (gameState == GameState.Ready) {
            Random random =  new Random(System.currentTimeMillis());
            turnIndex = random.nextInt(users.size());
        }

        gameState = GameState.Playing;
        declareValue = 0;
        
        users.forEach(player -> player.setCard(cardDeck.drawCard()));
        
        message = "Game Start! Next, " + users.get(turnIndex).getName() + "'s turn. Please raise or coyote.";
    }

    public void restartGame() {
        gameState = GameState.Ready;
        this.message = "Please wait for host user to start.";
    }



    public boolean canRaiseOrCoyote(UUID token) {
        if (gameState != GameState.Playing)
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

        gameState = GameState.Finish;

        List<Card> cards = users.stream().map(u -> u.getCard()).collect(Collectors.toList());

        Optional<User> secretCardUser = users.stream()
            .filter(u -> u.getCard().getCardType() == CardType.Secret)
            .findFirst();
        if (secretCardUser.isPresent()){
            Card subCard = this.cardDeck.drawCard();
            secretCardUser.get().setSubCard(subCard);
            cards.add(subCard);
        }
        
        if (containNightCard(cards))
            cardDeck.init();
        else
            cards.forEach(c -> cardDeck.discard(c));
        
        int total = Rule.calculateTotal(cards);
        message = users.get(turnIndex).getName() + " call Coyote! ";

        if (total < declareValue) {
            message += users.get(getPrevTurnIndex(turnIndex)).getName() + " loses... ("
                    + users.get(getPrevTurnIndex(turnIndex)).getName() + " raised " + Integer.toString(declareValue)
                    + ". " + "Total is " + Integer.toString(total) + ". )";
            turnIndex = getPrevTurnIndex(turnIndex);
            return users.get(getPrevTurnIndex(turnIndex));
        } else {
            message += users.get(turnIndex).getName() + " loses... (" + users.get(getPrevTurnIndex(turnIndex)).getName()
                    + " raised " + Integer.toString(declareValue) + ". " + "Total is " + Integer.toString(total)
                    + ". )";
            return users.get(turnIndex);
        }

    }
    
       
    private static boolean containNightCard(List<Card> cards) {
        for (Card card: cards)
            if (card.getCardType().equals(CardType.Night))
                return true;
        
        return false;
    }
}
