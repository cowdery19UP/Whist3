package edu.up.cs301.Whist;


import android.util.Log;

import java.util.ArrayList;

import edu.up.cs301.card.Card;
import edu.up.cs301.card.Suit;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.GameState;

/**
 * Created by Samuel on 11/9/2017.
 */

public class WhistGameState extends GameState {
    //turn integer incremented from 0-52 in a round
    public int turn = 0;
    //a cardStack of the cards in play on the TABLE
    public CardStack cardsInPlay;
    //an array of cards based on the player indexes
    public Card[] cardsByPlayerIdx = new Card[4];
    //a card stack of the cards that have been played in the ENTIRE ROUND
    public CardStack cardsPlayed;
    //an array of hands one for each player
    public Hand[] playerHands = new Hand[4];
    //the main deck the game is using
    public Deck mainDeck;
    //the leadingSuit of the trick
    public Suit leadSuit;
    //the index of the leading player
    public int leadPlayer;
    ///////////////substitute for Team Class////////////
    public int team1WonTricks = 0;
    public int team2WonTricks = 0;
    public int team1Points = 0;
    public int team2Points = 0;
    ///////////////substitute for Team Class/////////////
    //granding phase or nah
    public boolean grandingPhase = true;
    //whomst granded?
    public boolean team1Granded = false;
    public boolean team2Granded = false;


    //deciding for high and low round
    public boolean highGround = true;

    public WhistGameState(){
        //initial gameState
        Log.i("CreatedNewState","new");
        cardsInPlay = new CardStack();
        cardsPlayed = new CardStack();
        mainDeck  = new Deck();
        //deals the cards to the playerHands
        ///////lesson learned: cannot use for each loops to instantiate items in an array./////
        for(int i = 0; i<playerHands.length;i++){
            playerHands[i] = new Hand();
            for(int j = 0; j<13; j++){
                playerHands[i].add(mainDeck.dealRandomCard());
            }
        }
    }
    public WhistGameState(WhistGameState orig){
        //copy constructor
        Log.i("CreatedNewState","copy");
        //assigning basic instance variables as the same
        cardsInPlay = orig.cardsInPlay;
        cardsPlayed = orig.cardsPlayed;
        mainDeck  = new Deck(orig.mainDeck);
        turn = orig.turn;
        cardsByPlayerIdx = orig.cardsByPlayerIdx;
        leadSuit = orig.leadSuit;
        leadPlayer = orig.leadPlayer;
        team1Granded = orig.team1Granded;
        team2Granded = orig.team2Granded;
        team1Points = orig.team1Points;
        team1WonTricks = orig.team1WonTricks;
        team2Points = orig.team2Points;
        team2WonTricks = orig.team2WonTricks;
        grandingPhase = orig.grandingPhase;
        highGround = orig.highGround;

        //assigns hands to be separate in memory to avoid nulling out the mainState's hands
        for(int i = 0; i<playerHands.length;i++){
            playerHands[i] = new Hand();
            playerHands[i].stack = (ArrayList<Card>) orig.playerHands[i].stack.clone();
        }


    }//copyCtor

    public GameInfo sendGameState(){
        return this;
    }

    public int getTurn(){return turn;}

    public Hand getHand(){
        for(Hand d: playerHands) {
            if (d != null) {
                return d;
            }
        }

        return null;
    }


}
