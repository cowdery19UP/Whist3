package edu.up.cs301.Whist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.ArrayList;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.R;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;

/**
 * Created by PatrickMaloney on 11/7/17.
 */

public class WhistMainActivity extends GameMainActivity {

    public static final int PORT_NUMBER = 2278;
    //fun stuff! soundPool for playing superfluous audio
    public static SoundPool mySoundpool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
    public static int[] soundId = new int[8];
    public static Bitmap cardback;
    public String string;


    @Override
    public GameConfig createDefaultConfig(){
        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();
        // whist has two player types:  human and computer
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new WhistHumanPlayer(name);
            }});
        playerTypes.add(new GamePlayerType("Easy Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new WhistEasyComputerPlayer(name);
            }});
        playerTypes.add(new GamePlayerType("Hard Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new WhistHardComputerPlayer(name);
            }});
        playerTypes.add(new GamePlayerType("Normal Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new WhistComputerPlayer(name);
            }});


        // Create a game configuration class for Pig:
        GameConfig defaultConfig = new GameConfig(playerTypes, 4, 4, "Minnesota Whist", PORT_NUMBER);
        defaultConfig.addPlayer("Steve", 0); // player 1: a human player
        defaultConfig.addPlayer("Kevin", 1); // player 2: a computer player
        defaultConfig.addPlayer("Andrew",3);
        defaultConfig.addPlayer("Patrick",2);
        defaultConfig.setRemoteData("Sam", "", 0);
        return defaultConfig;

    }

    @Override
    public LocalGame createLocalGame() {
        //sets the int value for the sounds
        soundId[0] = mySoundpool.load(this, R.raw.airhorn,1);
        soundId[2] = mySoundpool.load(this, R.raw.stopitgethelp,1);
        soundId[1] = mySoundpool.load(this, R.raw.wow,1);
        soundId[3] = mySoundpool.load(this, R.raw.freerealestate,1);
        soundId[4] = mySoundpool.load(this, R.raw.timetoduel,1);
        soundId[5] = mySoundpool.load(this, R.raw.thatsaten,1);
        soundId[6] = mySoundpool.load(this, R.raw.yee,1);
        // get the bitmap for the card
        cardback = BitmapFactory.decodeResource(this.getResources(),R.drawable.cardback);
        return new WhistLocalGame();
    }
}
