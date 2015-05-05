package com.luke.cramcards;


public interface ViewInterface {
    void showCardSide(String s);


    void setKnown(boolean known);
    void goToNextCard();
    void goToPreviousCard();

    void shuffle();
    void flipCard();
    void goBack();
    void goForward();
    void restartAll();
    void restartShuffle();
    void restartUnknown();
    void quit();

}
