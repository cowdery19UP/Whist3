package edu.up.cs301.Whist;

import android.util.Log;

import org.junit.Test;

import edu.up.cs301.card.Card;
import edu.up.cs301.card.Suit;

import static org.junit.Assert.*;

/**
 * Created by PatrickMaloney on 11/15/17.
 */
public class WhistHumanPlayerTest {
    @Test
    public void testProgressPercent(){
        WhistHumanPlayer p = new WhistHumanPlayer("name");
        p.getMyHand().add(Card.fromString("2c"));
        p.getMyHand().add(Card.fromString("3c"));
        p.getMyHand().add(Card.fromString("4c"));
        p.getMyHand().add(Card.fromString("5c"));
        p.getMyHand().add(Card.fromString("6c"));

        p.onProgressChanged(null,60,true );

        assertTrue("wrong card selected"+p.selectedCard.shortName()+" index: "+p.i+" percent: "+p.p+"", p.selectedCard.equals(Card.fromString("5c")));
    }

    @Test
    public void testIfHasSuit(){
        CardStack d = new CardStack();
        d.add(Card.fromString("AC"));
        assertTrue(d.hasCardInSuit(Suit.Club));
    }
    @Test
    public void testCopyCtor(){
        WhistGameState state1 = new WhistGameState();
        WhistGameState state2 = new WhistGameState(state1);
        Card card1 = state1.playerHands[2].getCardByIndex(2);
        Card card2 = state2.playerHands[2].getCardByIndex(2);
        assertTrue("cards don't match",card1.equals(card2));
    }
}