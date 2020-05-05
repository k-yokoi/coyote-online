package net.kyokoi.coyote.entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class Rule {

    public static int calculateTotal(List<Card> cards) {
        int[] values = cards.stream()
                .mapToInt(c -> c.getCardValue())
                .toArray();

        if (containCardType(cards, CardType.x2)) {
            values = Arrays.stream(values)
                    .map(v -> v * 2)
                    .toArray();
        }

        return Arrays.stream(values).sum();
    }
    
    private static boolean containCardType(List<Card> cards, CardType cardType) {
        for (Card card: cards)
            if (card.getCardType().equals(cardType))
                return true;
        
        return false;
    }
    
}