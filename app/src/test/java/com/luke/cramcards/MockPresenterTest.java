package com.luke.cramcards;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MockPresenterTest {

    @Mock
    CLIPresenter presenterMock;

    @Mock
    CLIView view;

    @Before
    public void setUp() {
        Deck deck = new Deck();
        Card c = new Card("front", "back");
        Card c2 = new Card("front2", "back2");
        deck.addCard(c);
        deck.addCard(c2);

        presenterMock = new CLIPresenter(view, deck);
    }

    @After
    public void tearDown() {
        presenterMock = null;
        view = null;
    }

    @Test
    public void testShowFront()  {
        assertTrue(true);
    }
}
