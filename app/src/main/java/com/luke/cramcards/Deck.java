package com.luke.cramcards;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck;
    private int curCardIndex;

    public Deck(){
        this.deck = new ArrayList<Card>();
        curCardIndex = 0;
    }

    public void addCard(Card c){
        this.deck.add(c);
    }

    public int getNumCards(){
        return this.deck.size();
    }

    public Card getCurCard(){
        if(this.getNumCards() == 0){
            return null;
        }
        return this.deck.get(this.curCardIndex);
    }

    public void goToNext(){
        this.curCardIndex += 1;
    }
}
