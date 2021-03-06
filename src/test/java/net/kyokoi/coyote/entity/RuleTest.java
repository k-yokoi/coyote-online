package net.kyokoi.coyote.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;


public class RuleTest {

    @Test
    void calculateTotalTest_1() {
        List<Card> cards = new ArrayList<>();
        cards.add(Card.createBasicCard(20));
        cards.add(Card.createBasicCard(1));
        cards.add(Card.createBasicCard(-5));
        cards.add(Card.createBasicCard(2));

        int total = Rule.calculateTotal(cards);
        assertThat(total).isEqualTo(18);    
    }

    @Test
    void calculateTotalTest_2() {
        List<Card> cards = new ArrayList<>();
        cards.add(Card.createBasicCard(20));
        cards.add(Card.createBasicCard(1));
        cards.add(Card.createBasicCard(-5));
        cards.add(Card.createBasicCard(2));
        cards.add(Card.createX2Card());

        int total = Rule.calculateTotal(cards);
        assertThat(total).isEqualTo(36);    
    }

    @Test
    void calculateTotalTest_3() {
        List<Card> cards = new ArrayList<>();
        cards.add(Card.createBasicCard(20));
        cards.add(Card.createBasicCard(1));
        cards.add(Card.createBasicCard(-5));
        cards.add(Card.createBasicCard(2));
        cards.add(Card.createMax0Card());

        int total = Rule.calculateTotal(cards);
        assertThat(total).isEqualTo(-2);
    }
    
    @Test
    void calculateTotalTest_4() {
        List<Card> cards = new ArrayList<>();
        cards.add(Card.createBasicCard(20));
        cards.add(Card.createBasicCard(1));
        cards.add(Card.createBasicCard(-5));
        cards.add(Card.createBasicCard(2));
        cards.add(Card.createX2Card());
        cards.add(Card.createMax0Card());

        int total = Rule.calculateTotal(cards);
        assertThat(total).isEqualTo(-4);    
    }

    @Test
    void calculateTotalTest_5() {
        List<Card> cards = new ArrayList<>();
        cards.add(Card.createBasicCard(20));
        cards.add(Card.createBasicCard(1));
        cards.add(Card.createBasicCard(-5));
        cards.add(Card.createSecretCard());
        cards.add(Card.createBasicCard(2));

        int total = Rule.calculateTotal(cards);
        assertThat(total).isEqualTo(18);
    }
    
    @Test
    void calculateTotalTest_6() {
        List<Card> cards = new ArrayList<>();
        cards.add(Card.createBasicCard(20));
        cards.add(Card.createBasicCard(1));
        cards.add(Card.createBasicCard(-5));
        cards.add(Card.createBasicCard(2));
        cards.add(Card.createSecretCard());
        cards.add(Card.createX2Card());
        
        int total = Rule.calculateTotal(cards);
        assertThat(total).isEqualTo(36);    
    }

    @Test
    void calculateTotalTest_7() {
        List<Card> cards = new ArrayList<>();
        cards.add(Card.createBasicCard(20));
        cards.add(Card.createBasicCard(1));
        cards.add(Card.createBasicCard(-5));
        cards.add(Card.createBasicCard(2));
        cards.add(Card.createSecretCard());
        cards.add(Card.createMax0Card());

        int total = Rule.calculateTotal(cards);
        assertThat(total).isEqualTo(-2);
    }
    
    @Test
    void calculateTotalTest_8() {
        List<Card> cards = new ArrayList<>();
        cards.add(Card.createBasicCard(20));
        cards.add(Card.createBasicCard(1));
        cards.add(Card.createBasicCard(-5));
        cards.add(Card.createBasicCard(2));
        cards.add(Card.createSecretCard());
        cards.add(Card.createX2Card());
        cards.add(Card.createMax0Card());

        int total = Rule.calculateTotal(cards);
        assertThat(total).isEqualTo(-4);    
    }


}