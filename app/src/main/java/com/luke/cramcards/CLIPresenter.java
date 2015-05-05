package com.luke.cramcards;

public class CLIPresenter implements PresenterInterface{
    private CLIView view;
    private Deck model;
    private boolean show_front = true;

    public CLIPresenter(CLIView view, Deck model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void displayCard() {
        view.showCardSide(show_front ? model.getCurCard().getFront() : model.getCurCard().getBack());
    }

    public boolean showingFront(){
        return show_front;
    }

    @Override
    public void flipCard() {
        show_front = !show_front;
        displayCard();
    }

    @Override
    public void goBack() {

    }

    @Override
    public void goForward() {

    }

    @Override
    public void setKnown(boolean known) {

    }

    @Override
    public void restartAll() {

    }

    @Override
    public void restartShuffle() {

    }

    @Override
    public void restartUnknown() {

    }

    @Override
    public void quit() {

    }
}
