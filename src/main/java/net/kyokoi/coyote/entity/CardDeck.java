package net.kyokoi.coyote.entity;

import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

@Entity
public class CardDeck {
    private @Id @GeneratedValue Long id;
    @Embedded
    @ElementCollection
    @CollectionTable(
        name="deck_table",
        joinColumns=@JoinColumn(name="table_alpha_id")
    )
    private List<Card> deck;
    @Embedded
    @ElementCollection
    @CollectionTable(
        name="discard_table",
        joinColumns=@JoinColumn(name="table_alpha_id")
    )
    private List<Card> discard;
    private int index;
    @Transient
    private Random random;

    public CardDeck() {
        ;
    }
    
    public void init() {
        this.deck = new ArrayList<>();
        this.discard = new ArrayList<>();
        index = 0;
        
        setCard(Card.createBasicCard(20));
        setCards(Card.createBasicCard(15), 2);
        setCards(Card.createBasicCard(10), 3);
        setCards(Card.createBasicCard(5), 4);
        setCards(Card.createBasicCard(4), 4);
        setCards(Card.createBasicCard(3), 4);
        setCards(Card.createBasicCard(2), 4);
        setCards(Card.createBasicCard(1), 4);
        setCards(Card.createBasicCard(0), 3);
        setCards(Card.createBasicCard(-5), 2);
        setCard(Card.createBasicCard(-10));
        setCard(Card.createNightCard());
        setCard(Card.createX2Card());
        setCard(Card.createMax0Card());
        setCard(Card.createSecretCard());
        
        Collections.shuffle(this.deck, new Random(System.currentTimeMillis()));
    }

    private void setCards(Card card, final int number) {
        for (int i = 0; i < number; i++)
            setCard(card);
    }
    
    private void setCard(Card card) {
        this.deck.add(card);
    }

    public Card drawCard() {
        if (index >= deck.size()) {
            Collections.shuffle(discard, new Random(System.currentTimeMillis()));
            this.deck = discard;
            this.index = 0;
            this.discard = new ArrayList<>();
        }

        Card drawCard = deck.get(index);
        index++;
        return drawCard;
    }

    public void discard(Card card) {
        discard.add(card);
    }

}
