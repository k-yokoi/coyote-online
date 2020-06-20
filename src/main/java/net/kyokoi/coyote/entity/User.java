package net.kyokoi.coyote.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Entity
public class User implements Serializable {
    private @Id @GeneratedValue Long id;
    private String name;
    private int roomId;
    private UUID token;
    private boolean admin;
    @Embedded
    @ElementCollection(fetch=FetchType.EAGER)
    private List<Card> cards;
    
    public User() {}

    public User(String name, int roomId){
        this.name = name;
        this.roomId = roomId;
        this.token = UUID.randomUUID();
        this.admin = false;
        this.cards = new ArrayList<>();
    }

    public User(String name, int roomId, boolean admin){
        this.name = name;
        this.roomId = roomId;
        this.token = UUID.randomUUID();
        this.admin = admin;
        this.cards = new ArrayList<>();
    }

    public void setCard(Card card) {
        this.cards.clear();
        this.cards.add(card);
    }

    public Card getCard() {
        if (cards.isEmpty())
            return null;
        return cards.get(0);
    }

    public void setSubCard(Card card) {
        this.cards.add(card);
    }

    public String printCardValue(){
        if (cards.size() > 1)
            return cards.get(0).getCardString() + " > " + cards.get(1).getCardString();    
        return cards.get(0).getCardString();
    }

}

