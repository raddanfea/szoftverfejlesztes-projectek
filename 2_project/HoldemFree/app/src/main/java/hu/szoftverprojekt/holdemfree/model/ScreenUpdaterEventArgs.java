package hu.szoftverprojekt.holdemfree.model;

import java.util.ArrayList;

/**
 * A container class for the game data. This data will be passed to the method
 * which implements the invoke() method of the ScreenUpdater interface.
 *
 * So the fields are for example the data which you want to update on the screen (like the cards on the board)
 */
public class ScreenUpdaterEventArgs {
  public ArrayList<Player> players;
  public ArrayList<Card> board;
  public int moneyOnBoard;
  public int currentBet;
  public int dealerOffset;
  // extend this if needed, but remember to update the createEventArgs() function in the GameLogic too
}
