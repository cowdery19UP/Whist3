package edu.up.cs301.Whist;

import java.util.ArrayList;

import edu.up.cs301.card.Card;

/**
 * Created by Samuel on 11/9/2017.
 * This class serves as a holder for the 52 cards
 */

public class Deck extends CardStack {
    /**
     * This constructor instantiates a new deck of 52 cards
     */
    public Deck(){
        // creates a new deck with a stack of all 52 cards
        // using Professor Vegdahl's Card methods
        for (char s : "SHDC".toCharArray()) {
            for (char r : "KQJT98765432A".toCharArray()) {
                stack.add(Card.fromString(""+r+s));
            }
        }
    }//ctor
    /**
     * This is the copy constructor for the deck
     * @param orig -- the original deck to copy
     */
    public Deck(Deck orig){
        // synchronize to ensure that original is not being modified as we
        // iterate over it
        synchronized(orig.stack) {
            // create a new arrayList for our new deck; add each card in it
            stack = new ArrayList<Card>();
            for (Card c: orig.stack) {
                stack.add(c);
            }
        }
    }//CopyCtor

    /**
     * This method deals the "top" card of the cardstack
     * contained in Deck at index 0
     * @param player -- the player to deal the card to
     */
    public void dealTopCard(WhistComputerPlayer player){
        player.getMyHand().add(stack.get(0));
        stack.remove(stack.get(0));
    }

    /**
     * This method deals a random card from the cardstack
     * contained in Deck from a random index from 0-stack.size()
     * @param player -- the player to deal the card to
     */
    public void dealRandomCardToPlayer(WhistComputerPlayer player){
        int idx = (int)(Math.random()*stack.size());
        player.getMyHand().add(stack.get(idx));
        stack.remove(stack.get(idx));
    }
    /**
     * This method deals a random card from the cardstack
     * contained in Deck from a random index from 0-stack.size()
     *
     */
    public Card dealRandomCard(){
        int idx = (int)(Math.random()*stack.size());
        Card toReturn = stack.get(idx);
        stack.remove(stack.get(idx));
        return toReturn;
    }


    public boolean contains(Card c){
        for(Card a: this.stack){
            if(c.getRank() == a.getRank() && c.getSuit() == a.getSuit()) return true;
        }
        return false;
    }
}
