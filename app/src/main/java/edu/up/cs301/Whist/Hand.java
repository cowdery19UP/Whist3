package edu.up.cs301.Whist;

import java.util.ArrayList;
import java.util.Arrays;

import edu.up.cs301.card.Card;
import edu.up.cs301.card.Suit;

/**
 * Created by Samuel on 11/7/2017.
 * Hand is given to each player, it contains 13 cards at the
 * start of play and 0 at the end of a round
 */

public class Hand extends CardStack {

    //these 4 arrays hold the different suits to make it
    //easier to organize them
    ///////////////////////////////////////////////////
    private ArrayList<Card> hearts = new ArrayList<Card>();
    private ArrayList<Card> clubs = new ArrayList<Card>();
    private ArrayList<Card> diamonds = new ArrayList<Card>();
    private ArrayList<Card> spades = new ArrayList<Card>();
    ///////////////////////////////////////////////////////
    //this array of cards is used in Patrick's mergeSort function
    private static Card[] T = new Card[52];
    /**
     * The constructor for the Hand
     */
    public Hand(){

    }

    /**
     * This method organizes the cards in the hand by their suit
     *  by using Patrick's Sort function
     */
    public void organizeBySuit(){
        sortCards();
        for(Card c: stack){
            switch(c.getSuit()){
                case Club: clubs.add(c);
                    break;
                case Diamond: diamonds.add(c);
                    break;
                case Heart: hearts.add(c);
                    break;
                case Spade: spades.add(c);
            }
        }
        //clear the stack of cards to null
        stack.clear();
        //rebuild the stack from the ashes
        for(Card c: clubs){
            stack.add(c);
        }
        for(Card c: hearts){
            stack.add(c);
        }
        for(Card c: spades){
            stack.add(c);
        }
        for(Card c: diamonds){
            stack.add(c);
        }

    }

    private void sortCards(){
        Card[] arr = new Card[stack.size()]; //array used to sort

        for(int i = 0; i < arr.length; i++){ //copying stack to new array
            arr[i] = stack.get(i);
        }

        for(int i = 1; i < arr.length; i++){ //performing insertion sort
            Card key = arr[i];
            int j = i-1;
            while (j>=0 && arr[j].getRank().value(14) > key.getRank().value(14)){ //bumping all cards forward one after an insertion
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = key;
        }

        stack.clear(); //clear stack to copy array back in

        for(int i = 0; i < arr.length; i++){ //copying array back into CardStack
            stack.add(arr[i]);
        }
    }
}
