package net.kyokoi.coyote.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;


public class RuleTest {

    @Test
    void calculateTotalTest_1() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(20));
        cards.add(new Card(1));
        cards.add(new Card(-5));
        cards.add(new Card(2));

        int total = Rule.calculateTotal(cards);
        assertThat(total).isEqualTo(18);    
    }


    
}