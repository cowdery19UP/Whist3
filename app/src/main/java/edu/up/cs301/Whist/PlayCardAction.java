package edu.up.cs301.Whist;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.GamePlayer;

/**
 * Created by Samuel on 11/10/2017.
 */

public class PlayCardAction extends MoveAction {
    /**
     * constructor for GameAction
     *
     * @param player
     * 		the player who created the action
     */
    public PlayCardAction(GamePlayer player, Card c) {
        super(player);
        card = c;
    }

    public boolean isCardPlayAction() { return true; }
    public boolean isBidAction() { return false; }

}
