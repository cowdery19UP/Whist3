package edu.up.cs301.Whist;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.card.Card;
import edu.up.cs301.card.Suit;

/**
 * Created by Samuel on 11/7/2017.
 * Workhorse Class that holds an array list of cards called stack
 */

public class CardStack implements Serializable{

    protected ArrayList<Card> stack = new ArrayList<Card>();

    public CardStack(){

    }//mainCtor

    public CardStack(CardStack orig){
        for(Card c: orig.stack){
            stack.add(c);
        }
    }//copyCtor

    /**
     * This method goes through the stack of cards and
     * returns the card that is the greatest value rank
     * @return - the highest value card in any suit
     */
    public Card getHighest(){
        if(stack.size()==0) return null;
        //assigns the first card in the stack to be
        //the card to be returned
        Card highCard = stack.get(0);
        //indexes through the stack and if the c card is a higher
        //value, it becomes the highCard
        for(Card c: stack){
            if(c.getRank().value(14)>highCard.getRank().value(14)){
                highCard = c;
            }
        }
        return highCard;
    }
    /**
     * This method goes through the array of cards and
     * returns the card that is the greatest value rank
     * @return - the highest value card in any suit
     * @param inputStack -- an array of cards to sort
     */
    public Card getHighest(ArrayList<Card> inputStack){
        //assigns the first card in the stack to be
        //the card to be returned
        Card highCard = inputStack.get(0);
        //indexes through the stack and if the c card is a higher
        //value, it becomes the highCard
        for(Card c: inputStack){
            if(c.getRank().value(14)>highCard.getRank().value(14)){
                highCard = c;
            }
        }
        return highCard;
    }
    /**
     * This method goes through the stack of cards and
     * returns the card that is the least value rank
     * @return - the lowest value card in any suit
     */
    public Card getLowest(){
        if(stack.size()==0) return null;
        //assigns the first card in the stack to be
        //the card to be returned
        Card lowCard = stack.get(0);
        //indexes through the stack and if the c card is a lower
        //value, it becomes the lowCard
        for(Card c: stack){
            if(c.getRank().value(14)<lowCard.getRank().value(14)){
                lowCard = c;
            }
        }
        return lowCard;
    }
    /**
     * This method goes through the array of cards and
     * returns the card that is the lowest value rank
     * @return - the lowest value card in any suit
     * @param inputStack -- an array of cards to sort
     */
    public Card getLowest(ArrayList<Card> inputStack){
        //assigns the first card in the stack to be
        //the card to be returned
        Card lowCard = inputStack.get(0);
        //indexes through the stack and if the c card is a lower
        //value, it becomes the lowCard
        for(Card c: inputStack){
            if(c.getRank().value(14)<lowCard.getRank().value(14)){
                lowCard = c;
            }
        }
        return lowCard;
    }

    public Card getRandomCard(){
        //assigns the first card in the stack to be
        //the card to be returned
        Card RandCard = stack.get(0);
        //indexes through the stack and if the c card is a higher
        //value, it becomes the highCard
        for(Card c: stack) {
            Random random = new Random();
            int listSize = stack.size();
            int randomIndex = random.nextInt(listSize);
                RandCard = stack.get(randomIndex);

        }
        return RandCard;
    }

    /**
     * This method returns the highest value card
     * of a particular suit
     * @param suit -- the suit in which to get the high card
     * @return -- returns the highest card of that suit
     */
    public Card getHighestInSuit(Suit suit){
        if(stack.size()==0){
            Log.e("getLowestInSuit","cardStack stack is empty");
            return null;
        }
        //this is our array list of cards of a specific suit
        ArrayList<Card> cardsOfSuit = new ArrayList<Card>();
        //index through the stack and pull out the cards of the right suit
        for(Card c: stack){
            if(c.getSuit()==suit){
                cardsOfSuit.add(c);
            }
        }
        //if there are no cards of that suit return null
        if(cardsOfSuit.size()==0){
            return null;
        }
        //this is the card we will be returning
        Card theChosenOne = cardsOfSuit.get(0);
        //index through the array of suited cards and find the highest one
        for(Card c: cardsOfSuit){
            if(c.getRank().value(14)>theChosenOne.getRank().value(14)){
                theChosenOne = c;
            }
        }
        return theChosenOne;
    }
    /**
     * This method returns the lowest value card
     * of a particular suit
     * @param suit -- the suit in which to get the lowest card
     * @return -- returns the lowest card of that suit
     */
    public Card getLowestInSuit(Suit suit){
        if(stack.size()==0){
            Log.e("getLowestInSuit","cardStack stack is empty");
            return null;
        }
        //this is our array list of cards of a specific suit
        ArrayList<Card> cardsOfSuit = new ArrayList<Card>();
        //index through the stack and pull out the cards of the right suit
        for(Card c: stack){
            if(c.getSuit()==suit){
                cardsOfSuit.add(c);
            }
        }
        //if we didn't have any cards in that suit in this cardStack
        if(cardsOfSuit.size()==0){
            return null;
        }
        //this is the card we will be returning
        Card theChosenOne = cardsOfSuit.get(0);
        //index through the array of suited cards and find the lowest one
        for(Card c: cardsOfSuit){
            if(c.getRank().value(14)<theChosenOne.getRank().value(14)){
                theChosenOne = c;
            }
        }
        return theChosenOne;
    }

    /**
     * This method returns the random value card
     * of a particular suit
     * @param suit -- the suit in which to get the random card
     * @return -- returns the random card of that suit
     */

    public Card getRandomInSuit(Suit suit){
        if(stack.size()==0){
            Log.e("getLowestInSuit","cardStack stack is empty");
            return null;
        }
        //this is our array list of cards of a specific suit
        ArrayList<Card> cardsOfSuit = new ArrayList<Card>();
        //index through the stack and pull out the cards of the right suit
        for(Card c: stack){
            if(c.getSuit()==suit){
                cardsOfSuit.add(c);
            }
        }

        Card RandCard = cardsOfSuit.get(0);
        for(Card c: cardsOfSuit) {
            Random random = new Random();
            int listSize = cardsOfSuit.size();
            int randomIndex = random.nextInt(listSize);
            RandCard = cardsOfSuit.get(randomIndex);
        }


        return RandCard;
    }

    public int getIndexOfCard(Card c){
        for(int i=0;i<stack.size();i++){
            if(c.equals(stack.get(i))) return i;
        }
        return -1;
    }


    /**
     * This method removes a card from the stack
     * @param beGone -- the card to be removed
     */
    public void remove(Card beGone){
        synchronized (stack) {
            stack.remove(beGone);
        }
    }

    public void removeAll(){stack.clear();}
    /**
     * This method adds a card to the stack
     * @param addMe -- the card to be added
     */
    public void add(Card addMe){
        synchronized (stack) {
            //if we actually have a card to add, add it
            if(addMe != null) stack.add(addMe);
        }
    }

    public ArrayList<Card> getStack(){
        return this.stack;
    }

    public int getSize(){ return stack.size(); }

    public Card getCardByIndex(int index){ return stack.get(index); }

    public boolean hasCardInSuit(Suit s){
        for(Card c: stack){
            if(c.getSuit().equals(s)){
                return true;
            }
        }
        return false;
    }

    public boolean contains(Card c){
        for(Card d : stack){
            if(d.equals(c)) return true;
        }
        return false;
    }


}
