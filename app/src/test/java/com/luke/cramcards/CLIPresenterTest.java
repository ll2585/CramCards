package com.luke.cramcards;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;
import static org.junit.contrib.java.lang.system.StandardOutputStreamLog.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class CLIPresenterTest {

    CLIPresenter presenter;


    BufferedReader bufferedReader;


    CLIView view;

    @Before
    public void setUp() {
        bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        view = Mockito.spy(new CLIView());
        view.setReader(bufferedReader);
        Deck deck = new Deck();
        Card c = new Card("front", "back");
        Card c2 = new Card("front2", "back2");
        deck.addCard(c);
        deck.addCard(c2);

        presenter = new CLIPresenter(view, deck);
        view.setPresenter(presenter);
    }

    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog();

    @Test
    public void testShowFront() throws IOException {
        assertTrue(presenter.showingFront());
        when(bufferedReader.readLine()).thenReturn("c").thenReturn("f").thenReturn("x");
        //when(view.responseIsGood(anyString())).thenReturn(true);
        presenter.displayCard();
        assertTrue(log.getLog().contains("front"));
        assertTrue(log.getLog().contains("f)lip/(n)ext/(p)revious/(k)now"));
        assertTrue(log.getLog().contains("Try again"));

    }
}
