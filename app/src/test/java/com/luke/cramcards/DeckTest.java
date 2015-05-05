package com.luke.cramcards;

import org.junit.Assert;
import org.junit.Test;

public class DeckTest {
    @Test
    public void hasCards() {
        Deck d = new Deck();
        Card c = new Card("front", "back");
        d.addCard(c);
        Assert.assertTrue(d.getNumCards() == 1);
        Assert.assertTrue(d.getCurCard().getFront().equals("front"));
        Assert.assertTrue(d.getCurCard().getBack().equals("back"));
    }

    @Test
    public void hasNoCards() {
        Deck d = new Deck();
        Assert.assertTrue(d.getNumCards() == 0);
        Assert.assertNull(d.getCurCard());
    }

    @Test
    public void increasedCard() {
        Deck d = new Deck();
        Card c = new Card("front", "back");
        Card c2 = new Card("front2", "back2");
        d.addCard(c);
        d.addCard(c2);
        d.goToNext();
        Assert.assertTrue(d.getCurCard().getFront() == "front2");
    }
}