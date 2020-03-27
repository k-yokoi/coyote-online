package net.kyokoi.coyote.entity;

import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cards {
    private final List<Card> cards;
    private final List<Boolean> isUsed;
    private final int cardsSize;
    private int numDraw;
    Random random;


    public Cards(){
        cards = new ArrayList<>();
        isUsed = new ArrayList<>();
        numDraw  = 0;
        random = new Random(System.currentTimeMillis());

        setCard(20, 1);
        setCard(15, 2);
        setCard(10, 3);
        setCard(5, 4);
        setCard(4, 4);
        setCard(3, 4);
        setCard(2, 4);
        setCard(1, 4);
        setCard(0, 4);
        setCard(-5, 2);
        setCard(-10, 1);

        this.cardsSize = cards.size();
    }

    private void setCard(int value, int number) {
        for (int i=0; i<number; i++) {
            cards.add(new Card(value));
            isUsed.add(false);
        }
    }

    public Card drawCard() {
        if (cardsSize <= numDraw)
            return null;

        int index = random.nextInt(cardsSize);
        while (isUsed.get(index)) {
            index = random.nextInt(cardsSize);
        }
        isUsed.set(index, true);
        numDraw++;
        return cards.get(index);

    }

}
