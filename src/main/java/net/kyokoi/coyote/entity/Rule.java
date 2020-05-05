package net.kyokoi.coyote.entity;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class Rule {

    public static int calculateTotal(List<Card> cards) {
        return cards.stream()
                .mapToInt(c -> c.getValue())
                .sum();
	}
    
}