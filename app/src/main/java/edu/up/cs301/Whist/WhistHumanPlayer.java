package edu.up.cs301.Whist;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import edu.up.cs301.animation.AnimationSurface;
import edu.up.cs301.animation.Animator;
import edu.up.cs301.card.Card;
import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.util.ArrayList;


/**
 * Created by PatrickMaloney on 11/7/17.
 */

public class WhistHumanPlayer extends GameHumanPlayer implements Animator, OnClickListener {
    //the main activity for the player
    private GameMainActivity myActivity;
    //the unused background color
    private int backgroundColor = Color.BLACK;
    //the hand of the player with all cards
    private Hand myHand = new Hand();
    //the selected card of the player
    public Card selectedCard;
    //the saved state to be accessed by the player
    private WhistGameState savedState;
    //the animation surface for the user interface
    private AnimationSurface Tablesurface;
    //the widgets to the gui
    private Button playCardButton;
    private boolean hasTouched = false;

    private int selectedIdx = 5;
    private RectF[] cardIndicatorSpots = new RectF[13];
    private RectF[] handSpots = new RectF[13];
    private RectF[] tableSpots = new RectF[4];




    public WhistHumanPlayer(String name){
        super(name);

    }

    public void setAsGui(GameMainActivity activity){

        // remember the activity
        this.myActivity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.whist_layout);

        // link the animator (this object) to the animation surface
        Tablesurface = (AnimationSurface) myActivity
                .findViewById(R.id.animationSurface);
        Tablesurface.setAnimator(this);

        playCardButton = (Button) myActivity.findViewById(R.id.play_card_button);
        playCardButton.setOnClickListener(this);

        // read in the card images
        Card.initImages(activity);


        // if the state is not null, simulate having just received the state so that
        // any state-related processing is done
        if (savedState != null) {
            receiveInfo(savedState);
        }

    }

    /**
     * when the game calls sendInfoTo...this method is called
     *
     * @param info
     */
    @Override
    public void receiveInfo(GameInfo info){
        //check if the info is the correct type
        if(!(info instanceof WhistGameState)){
            return;
        }
        //check if state is null
        if(info==null){
            return;
        }
        //updates the player's gamestate
        savedState = (WhistGameState) info;
        //updates the player hand from the new gamestate
        myHand = savedState.getHand();
        myHand.organizeBySuit();
        if(myHand.getSize()==13){
            selectedCard = myHand.getCardByIndex(myHand.getSize()/2);
        }

    }

    @Override
    public View getTopView(){

        //return activity.findViewById(R.id.top_gui_layout);
        return null;
    }
    //returns the player hand
    public Hand getMyHand(){ return myHand;}

    /**
     * This is the main animation method in the class that draws the GUI for
     * the human player. It includes methods that handle the text colors on screen
     * and the color of the playcard button for the user.
     * @param g - the canvas object to be used
     */
    public void tick(Canvas g) {

        //checks to make sure there is a state to pull information from
        if (savedState != null) {
            //set rectangles for hand and table spots

            setHandSpots();
            setTableSpots(playerNum);

            //drawing the table on the GUI
            Paint tableIn = new Paint();
            Paint tableOut = new Paint();
            tableOut.setColor(Color.rgb(42, 111, 0));
            tableIn.setColor(Color.rgb(104, 69, 0));
            RectF rectIn = new RectF(40, 50, Tablesurface.getWidth() - 40, (int) (Tablesurface.getBottom() * 0.69));
            RectF rectOut = new RectF(70, 80, Tablesurface.getWidth() - 70, (int) (Tablesurface.getBottom() * 0.66));
            g.drawOval(rectIn, tableIn);
            g.drawOval(rectOut, tableOut);
            //drawing text on the GUI
            Paint paintStaticText = new Paint();
            paintStaticText.setColor(Color.WHITE);
            paintStaticText.setTextSize(45);
            g.drawText("Overall Scores", 10, 40, paintStaticText);
            paintStaticText.setTextSize(40);
            paintStaticText.setColor(Color.rgb(102, 204, 255));
            g.drawText("Team 1: " + savedState.team1Points, 10, 75, paintStaticText);
            paintStaticText.setColor(Color.RED);
            g.drawText("Team 2: " + savedState.team2Points, 10, 110, paintStaticText);
            paintStaticText.setColor(Color.WHITE);
            paintStaticText.setTextSize(45);
            g.drawText("Current Tricks", Tablesurface.getWidth() - 260, 40, paintStaticText);
            paintStaticText.setTextSize(40);
            //paints text for each team and indicates the team that has granded with a G
            if (savedState.team1Granded) {
                paintStaticText.setColor(Color.rgb(102, 204, 255));
                g.drawText("Team 1: " + savedState.team1WonTricks + "  G", Tablesurface.getWidth() - 260, 75, paintStaticText);
                paintStaticText.setColor(Color.RED);
                g.drawText("Team 2: " + savedState.team2WonTricks, Tablesurface.getWidth() - 260, 110, paintStaticText);
            } else if (savedState.team2Granded) {
                paintStaticText.setColor(Color.rgb(102, 204, 255));
                g.drawText("Team 1: " + savedState.team1WonTricks, Tablesurface.getWidth() - 260, 75, paintStaticText);
                paintStaticText.setColor(Color.RED);
                g.drawText("Team 2: " + savedState.team2WonTricks + "  G", Tablesurface.getWidth() - 260, 110, paintStaticText);
            } else {
                paintStaticText.setColor(Color.rgb(102, 204, 255));
                g.drawText("Team 1: " + savedState.team1WonTricks, Tablesurface.getWidth() - 260, 75, paintStaticText);
                paintStaticText.setColor(Color.RED);
                g.drawText("Team 2: " + savedState.team2WonTricks, Tablesurface.getWidth() - 260, 110, paintStaticText);
            }
            //establishes the RectF's for each player's spot
            RectF bottomRectIndicator = new RectF(Tablesurface.getWidth() / 2 - (allPlayerNames[0].length() * 10) - 40, (Tablesurface.getHeight() / 20) * 13 - 65,
                    Tablesurface.getWidth() / 2 - (allPlayerNames[0].length() * 10) + allPlayerNames[0].length() * 18 + 40, (Tablesurface.getHeight() / 20) * 13 + 40);

            RectF rightRectIndicator = new RectF(Tablesurface.getWidth() / 20 * 15 - (allPlayerNames[1].length() * 9) - 40, ((Tablesurface.getHeight() / 20) * 11) - 95,
                    Tablesurface.getWidth() / 20 * 15 - (allPlayerNames[1].length() * 9) + allPlayerNames[1].length() * 18 + 40, ((Tablesurface.getHeight() / 20) * 11) + 10);

            RectF topRectIndicator = new RectF(Tablesurface.getWidth() / 2 - (allPlayerNames[0].length() * 10) - 40, Tablesurface.getHeight() / 10 - 65,
                    Tablesurface.getWidth() / 2 - (allPlayerNames[0].length() * 10) + allPlayerNames[0].length() * 20 + 40, Tablesurface.getHeight() / 10 + 40);

            RectF leftRectIndicator = new RectF(Tablesurface.getWidth() / 20 * 5 - (allPlayerNames[3].length() * 6) - 40, ((Tablesurface.getHeight() / 20) * 11) - 95,
                    Tablesurface.getWidth() / 20 * 5 - (allPlayerNames[3].length() * 6) + allPlayerNames[1].length() * 20 + 40, ((Tablesurface.getHeight() / 20) * 11) + 10);
            Paint BackgroundboxPainter = new Paint();
            ////places a black box around the player name whose turn it is
            BackgroundboxPainter.setColor(Color.BLACK);
            if (savedState.getTurn() == playerNum)
                g.drawRect(bottomRectIndicator, BackgroundboxPainter);
            else if (savedState.getTurn() == (playerNum + 1) % 4)
                g.drawRect(rightRectIndicator, BackgroundboxPainter);
            else if (savedState.getTurn() == (playerNum + 2) % 4)
                g.drawRect(topRectIndicator, BackgroundboxPainter);
            else if (savedState.getTurn() == (playerNum + 3) % 4)
                g.drawRect(leftRectIndicator, BackgroundboxPainter);

            Paint myTeamPainter = new Paint();
            Paint otherTeamPainter = new Paint();
            //paints the player names with team colors
            if (playerNum % 2 == 0) {
                myTeamPainter.setColor(Color.rgb(102, 204, 255));
                otherTeamPainter.setColor(Color.RED);
            } else {
                otherTeamPainter.setColor(Color.rgb(102, 204, 255));
                myTeamPainter.setColor(Color.RED);
            }
            otherTeamPainter.setTextSize(35);
            myTeamPainter.setTextSize(35);

            g.drawText(allPlayerNames[playerNum], Tablesurface.getWidth() / 2 - (allPlayerNames[playerNum].length() * 10), (Tablesurface.getHeight() / 20) * 13, myTeamPainter);
            g.drawText(allPlayerNames[(playerNum + 2) % 4], Tablesurface.getWidth() / 2 - (allPlayerNames[(playerNum + 2) % 4].length() * 10), Tablesurface.getHeight() / 10, myTeamPainter);

            g.drawText(allPlayerNames[(playerNum + 1) % 4], Tablesurface.getWidth() / 20 * 15 - (allPlayerNames[1].length() * 9), (Tablesurface.getHeight() / 20) * 11 - 30, otherTeamPainter);
            g.drawText(allPlayerNames[(playerNum + 3) % 4], Tablesurface.getWidth() / 20 * 5 - (allPlayerNames[3].length() * 6), (Tablesurface.getHeight() / 20) * 11 - 30, otherTeamPainter);
            //resets the paint object to white to begin the next tick
            paintStaticText.setColor(Color.WHITE);
            //Paints a nice reminder of Granding phase for the player
            if (savedState.grandingPhase) {
                g.drawText("GRANDING PHASE", Tablesurface.getWidth() / 2 - 150, Tablesurface.getHeight() / 10 * 4 - 30, paintStaticText);
            }
            else if(savedState.cardsPlayed.getSize()==52){
                g.drawText("END ROUND", Tablesurface.getWidth() / 2 - 120, Tablesurface.getHeight() / 10 * 4 - 30, paintStaticText);
            }
            else if(savedState.cardsInPlay.getSize()==4){
                g.drawText("END TRICK", Tablesurface.getWidth() / 2 - 110, Tablesurface.getHeight() / 10 * 4 - 30, paintStaticText);
            }


            //in order to make the GUI more user friendly, I added a handler to make the playCard button
            //light up green when it is this player's turn to play
            //////////////////////////////Button Handled////////////////////////////////////////
            Handler refresh = new Handler(Looper.getMainLooper());
            refresh.post(new Runnable() {
                public void run() {
                    //if the leadSuit has been established and we have the suit
                    if (savedState.cardsInPlay.getSize() != 0) {
                        if (savedState.getTurn() == playerNum && myHand.hasCardInSuit(savedState.leadSuit)) {
                            //button turn green to indicate legal card play
                            if (selectedCard != null) {
                                if (selectedCard.getSuit().equals(savedState.leadSuit)) {
                                    playCardButton.setBackgroundColor(Color.GREEN);
                                    playCardButton.setEnabled(true);
                                } else {
                                    playCardButton.setBackgroundColor(Color.DKGRAY);
                                    playCardButton.setEnabled(false);
                                }
                            } else {
                                playCardButton.setBackgroundColor(Color.DKGRAY);
                                playCardButton.setEnabled(false);
                            }

                        }
                        //if we do not have the suit of the leadSuit, set button to a darker, sadder green
                        else if (savedState.getTurn() % 4 == playerNum && !myHand.hasCardInSuit(savedState.leadSuit)) {
                            //if it is granding phase, any card will do
                            if (savedState.grandingPhase) {
                                playCardButton.setBackgroundColor(Color.GREEN);
                                playCardButton.setEnabled(true);
                            }
                            //it isn't granding phase, and you can't follow suit
                            else {
                                playCardButton.setBackgroundColor(Color.rgb(34, 139, 34));
                                playCardButton.setEnabled(true);
                            }
                        }
                        //for all other times, it is not our turn and it is not legal to play
                        else {
                            playCardButton.setBackgroundColor(Color.DKGRAY);
                            playCardButton.setEnabled(false);
                        }
                    }
                    //there are no cards in play, the hand has just started
                    else {
                        //if it is our turn, all cards will be good to lead
                        if (savedState.getTurn() % 4 == playerNum) {
                            playCardButton.setBackgroundColor(Color.GREEN);
                            playCardButton.setEnabled(true);
                        }
                        //it is not our turn, do not play
                        else {
                            playCardButton.setBackgroundColor(Color.DKGRAY);
                            playCardButton.setEnabled(false);
                        }
                    }

                }
            });
            ////////////////////////Button Handled/////////////////////////////

            //assigns and paints the cards in play that will appear on the table
            if (savedState.cardsInPlay != null) {
                synchronized (g) {
                    setTableDisplay(g);
                }
            }

            //drawCard(g, handSpots[13], selectedCard);

            for (int i = 0; i < myHand.getSize(); i++) {
                drawCard(g, handSpots[(myHand.getSize()-1)-i], myHand.getCardByIndex(i));
            }
            if(savedState.turn == getPlayerIdx() && hasTouched) {
                g.drawOval(cardIndicatorSpots[selectedIdx], myTeamPainter);
            }
        }

    }
    /**
     * @return
     * 		the amimation interval, in milliseconds
     */
    public int interval() {
        // 1/20 of a second
        return 50;
    }

    /**
     * @return
     * 		the background color
     */
    public int backgroundColor() {
        return backgroundColor;
    }

    /**
     * @return
     * 		whether the animation should be paused
     */
    public boolean doPause() {
        return false;
    }

    /**
     * @return
     * 		whether the animation should be terminated
     */
    public boolean doQuit() {
        return false;
    }

    /**
     * callback method: we have received a touch on the animation surface
     *
     * @param event
     * 		the motion-event
     */
    public void onTouch(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int top = (Tablesurface.getHeight()/2)-133+450;
        int bottom = (Tablesurface.getHeight()/2)+133+450;
        RectF handArea = new RectF(0,top,Tablesurface.getWidth(),bottom);
        if(handArea.contains(x,y)) {
            for (int i = 0; i < myHand.getSize(); i++) {
                if (handSpots[i].contains(x, y)) {
                    selectedCard = myHand.getCardByIndex((myHand.getSize()-1)-i);
                    selectedIdx = i;

                    break;
                }
            }
            hasTouched = true;
        }


    }

    /**
     * draws a card on the canvas; if the card is null, draw a card-back
     *
     * @param g
     * 		the canvas object
     * @param rect
     * 		a rectangle defining the location to draw the card
     * @param c
     * 		the card to draw; if null, a card-back is drawn
     */
    private static void drawCard(Canvas g, RectF rect, Card c) {
        if (c == null) {
            // create the paint object
            Paint p = new Paint();
            p.setColor(Color.BLACK);
            // create the source rectangle
            Rect r = new Rect(0,0,WhistMainActivity.cardback.getWidth(),WhistMainActivity.cardback.getHeight());
            // draw the bitmap into the target rectangle
            g.drawBitmap(WhistMainActivity.cardback, r, rect, p);
        }
        else {
            // just draw the card
            c.drawOn(g, rect);
        }
    }

    /**
     * This method sets the GUI display of the cards on the table
     * @param g - the canvas object to draw on
     */
    //TODO There is still a concurrent modification exception at this method as of 12/2/2017
    private void setTableDisplay(Canvas g){
           int Startspot = savedState.leadPlayer;
           ArrayList<Card> stackCopy = (ArrayList<Card>)savedState.cardsInPlay.stack.clone();
           for (Card c : stackCopy) {
               drawCard(g, tableSpots[Startspot % 4], c);
               Startspot++;
           }
    }

    private void setHandSpots(){
        int middle = Tablesurface.getWidth()/2;
        int top = (Tablesurface.getHeight()/2)-133+450;
        int bottom = (Tablesurface.getHeight()/2)+133+450;

        //handSpots[13] = new RectF(middle+700, top, middle+900 , bottom);
        for(int i = 0; i<myHand.getSize();i++){
            handSpots[i] = new RectF((0+(i*150)),top,200+(i*150),bottom);
            //handSpots[i] = new RectF(middle-100-(-350+(i*100)),top,middle+100-(-350+(i*100)),bottom);
        }
        for(int i = 0; i<myHand.getSize();i++){
            cardIndicatorSpots[i] = new RectF((0+(i*150)),top-40,200+(i*150),top);
        }

    }
    private void setTableSpots(int mySpot){
        //the spot in front of the human player
        tableSpots[mySpot] = new RectF((Tablesurface.getWidth()/2)-100,(Tablesurface.getHeight()/2)-133,
                (Tablesurface.getWidth()/2)+100,(Tablesurface.getHeight()/2)+133);
        //the spot across from the humanPlayer
        tableSpots[(mySpot+2)%4] = new RectF((Tablesurface.getWidth()/2)-100,(Tablesurface.getHeight()/2)-133-330,
                (Tablesurface.getWidth()/2)+100,(Tablesurface.getHeight()/2)+133-330);
        //the spot to the left of the human player
        tableSpots[(mySpot+3)%4] = new RectF((Tablesurface.getWidth()/2)-100-500,(Tablesurface.getHeight()/2)-133-150,
                (Tablesurface.getWidth()/2)+100-500,(Tablesurface.getHeight()/2)+133-150);
        //the spot to the right of the human player
        tableSpots[(mySpot+1)%4] = new RectF((Tablesurface.getWidth()/2)-100+500,(Tablesurface.getHeight()/2)-133-150,
                (Tablesurface.getWidth()/2)+100+500,(Tablesurface.getHeight()/2)+133-150);
    }

    /**
     * scales a rectangle, moving all edges with respect to its center
     *
     * @param rect
     * 		the original rectangle
     * @param factor
     * 		the scaling factor
     * @return
     * 		the scaled rectangle
     */
    private static RectF scaledBy(RectF rect, float factor) {
        // compute the edge locations of the original rectangle, but with
        // the middle of the rectangle moved to the origin
        float midX = (rect.left+rect.right)/2;
        float midY = (rect.top+rect.bottom)/2;
        float left = rect.left-midX;
        float right = rect.right-midX;
        float top = rect.top-midY;
        float bottom = rect.bottom-midY;

        // scale each side; move back so that center is in original location
        left = left*factor + midX;
        right = right*factor + midX;
        top = top*factor + midY;
        bottom = bottom*factor + midY;
        // create/return the new rectangle
        return new RectF(left, top, right, bottom);
    }
    public void onClick(View v){
        if(v instanceof Button) {
            Button b = (Button) v;
            if (selectedCard != null) {
                if(savedState.grandingPhase){
                    game.sendAction(new BidAction(this, selectedCard));
                }
                else game.sendAction(new PlayCardAction(this, selectedCard));
                b.setBackgroundColor(Color.DKGRAY);
                selectedCard = null;
                hasTouched = false;
            }
            else flash(Color.RED,1000);
        }

    }



    public int getPlayerIdx(){
        return playerNum;
    }
}
