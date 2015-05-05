package com.luke.cramcards;

import org.junit.Assert;
import org.junit.Test;

public class CardTest {
    @Test
    public void hasFrontAndBack() {
        Card c = new Card("front", "back");
        Assert.assertTrue(c.getFront() == "front");
        Assert.assertTrue(c.getBack() == "back");
    }
}