package com.luke.cramcards;

public class Card {
    private String front;
    private String back;

    public Card(String front, String back){
        this.front = front;
        this.back = back;
    }

    public String getFront(){
        return this.front;
    }

    public String getBack(){
        return this.back;
    }
}
