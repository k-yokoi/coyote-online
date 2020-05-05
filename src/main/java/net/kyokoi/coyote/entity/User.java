package net.kyokoi.coyote.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Entity
public class User implements Serializable {
    private @Id @GeneratedValue Long id;
    private String name;
    private UUID roomId;
    private UUID token;
    private boolean admin;
    @Setter
    @Column(nullable=true)
    private Card card;

    public User() {}

    public User(String name, UUID roomId){
        this.name = name;
        this.roomId = roomId;
        this.token = UUID.randomUUID();
        this.admin = false;
    }

    public User(String name, UUID roomId, boolean admin){
        this.name = name;
        this.roomId = roomId;
        this.token = UUID.randomUUID();
        this.admin = admin;
    }

    public String printCardValue(){
        if (card==null)
            return "# No Card";
        return Integer.toString(card.getCardValue());
    }

}

