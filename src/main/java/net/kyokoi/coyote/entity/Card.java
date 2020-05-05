package net.kyokoi.coyote.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Card {
    @Column(nullable=true)
    private String cardString;
    @Column(nullable=true)
    private int cardValue;
    @Column(nullable=true)
    private CardType cardType;
    
    public static Card createBasicCard(final int value) {
        return Card.builder()
                .cardValue(value)
                .cardType(CardType.Basic)
                .cardString(Integer.toString(value))
                .build();
    }
    
    public static Card createX2Card() {
        return Card.builder()
                .cardType(CardType.x2)
                .cardString("x2")
                .build();
    }
}
