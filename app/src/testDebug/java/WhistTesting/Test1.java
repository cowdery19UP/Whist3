package WhistTesting;


import android.util.Log;
import android.widget.SeekBar;

import org.junit.Test;

import edu.up.cs301.Whist.CardStack;
import edu.up.cs301.Whist.Deck;
import edu.up.cs301.Whist.WhistComputerPlayer;
import edu.up.cs301.Whist.WhistGameState;
import edu.up.cs301.Whist.WhistHumanPlayer;
import edu.up.cs301.card.Card;
import edu.up.cs301.card.Rank;
import edu.up.cs301.card.Suit;

import static org.junit.Assert.assertTrue;

/**
 * Created by Samuel on 11/9/2017.
 */

public class Test1 {

    @Test
    public void testHighSuit() throws Exception{
        Deck myDeck = new Deck();
        Card aceClubs = Card.fromString("AC");
        Card highClub = myDeck.getHighestInSuit(Suit.Club);
        assertTrue("highClub!",aceClubs.equals(highClub));
    }
    @Test
    public void testLowSuit(){
        Deck myDeck = new Deck();
        Card aceClubs = Card.fromString("2C");
        System.out.print(""+aceClubs.shortName());
        Card lowClub = myDeck.getLowestInSuit(Suit.Club);
        assertTrue("lowClub!",aceClubs.equals(lowClub));
    }


    @Test
    public void testHigh(){
        CardStack d = new CardStack();
        d.add(Card.fromString("4D"));
        d.add(Card.fromString("JS"));
        d.add(Card.fromString("AC"));
        d.add(Card.fromString("8H"));
        d.add(Card.fromString("QD"));
        d.add(Card.fromString("4C"));

        Card highest = d.getHighest();
        assertTrue("highest in deck",highest.getRank() == Rank.ACE);
    }

    @Test
    public void testLow(){
        CardStack d = new CardStack();
        d.add(Card.fromString("4D"));
        d.add(Card.fromString("JS"));
        d.add(Card.fromString("AC"));
        d.add(Card.fromString("8H"));
        d.add(Card.fromString("QD"));
        d.add(Card.fromString("4C"));
        d.add(Card.fromString("2C"));


        Card lowest = d.getLowest();
        assertTrue("lowest in deck",lowest.getRank() == Rank.TWO);
    }

    @Test
    public void testDeckCtor(){
        Deck d = new Deck();
        for(char s : "SHDC".toCharArray()){
            for(char r : "KQJT98765432A".toCharArray()){
                assertTrue("Not all cards in deck", d.contains(Card.fromString(""+r+s)));
            }
        }
    }

    @Test
    public void testDealRandom(){
        Deck d = new Deck();
        //d.dealRandomCard(new WhistComputerPlayer("computer"));
        assertTrue("Card not removed from deck", d.getStack().size() < 52 );
    }

    @Test
    public void testSeekBarPerent(){
        WhistHumanPlayer p = new WhistHumanPlayer("name");
        p.getMyHand().add(Card.fromString("2c"));
        p.getMyHand().add(Card.fromString("3c"));
        p.getMyHand().add(Card.fromString("4c"));
        p.getMyHand().add(Card.fromString("5c"));
        p.getMyHand().add(Card.fromString("6c"));

        p.onProgressChanged(null,60,true );

        assertTrue("wrong card selected", p.selectedCard.equals(Card.fromString("5c")));
    }
    @Test
    public void testCopyCtor(){
        WhistGameState state1 = new WhistGameState();
        WhistGameState state2 = new WhistGameState(state1);
        assertTrue("cards don't match",state1.playerHands[2].getCardByIndex(2).equals(state2.playerHands[2].getCardByIndex(2)));
    }
}
