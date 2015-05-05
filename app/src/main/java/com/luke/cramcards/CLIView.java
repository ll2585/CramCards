package com.luke.cramcards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class CLIView implements ViewInterface{

    private PresenterInterface presenter;
    private BufferedReader buffered_reader;
    private PrintStream out;
    private String prompt = "(f)lip/(n)ext/(p)revious/(k)now/e(x)it: ";

    public CLIView(BufferedReader buffered_reader){
        this.buffered_reader = buffered_reader;
        this.out = System.out;
    }

    public CLIView(){ //for mocking...
        this.out = System.out;
    }

    public void setReader(BufferedReader reader){
        this.buffered_reader = reader;
    }

    public void setPresenter(PresenterInterface presenter){
        this.presenter = presenter;
    }
    @Override
    public void showCardSide(String s) {
        this.out.println(s);
        this.out.print(prompt);
        String user_prompt = null;
        try {
            user_prompt = this.buffered_reader.readLine();
            do{
                if(user_prompt.equals("f")){
                    //this.presenter.flipCard();
                }else if(user_prompt.equals("n")){

                }else if(user_prompt.equals("p")){

                }else if(user_prompt.equals("k")){

                }else if(user_prompt.equals("x")){

                }else{
                    this.out.println("Try again.");
                    this.out.print(prompt);
                    user_prompt = this.buffered_reader.readLine();
                }
            } while(!responseIsGood(user_prompt));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean responseIsGood(String s){
        if(s.equals("x") || s.equals("f") || s.equals("b")){
            return true;
        }
        return false;
    }


    @Override
    public void setKnown(boolean known) {

    }

    @Override
    public void goToNextCard() {

    }

    @Override
    public void goToPreviousCard() {

    }

    @Override
    public void shuffle() {

    }

    @Override
    public void flipCard() {

    }

    @Override
    public void goBack() {

    }

    @Override
    public void goForward() {

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
