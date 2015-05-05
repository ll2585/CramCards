package com.luke.cramcards;

public interface PresenterInterface {

    public void displayCard();
    public void flipCard();
    public void goBack();
    public void goForward();
    public void setKnown(boolean known);
    public void restartAll();
    public void restartShuffle();
    public void restartUnknown();
    public void quit();
}
