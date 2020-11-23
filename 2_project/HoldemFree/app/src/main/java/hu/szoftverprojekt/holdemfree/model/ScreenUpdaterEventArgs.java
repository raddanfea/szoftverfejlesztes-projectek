package hu.szoftverprojekt.holdemfree.model;

import java.util.ArrayList;

/**
 * A container class for the game data. This data will be passed to the method
 * which implements the invoke() method of the ScreenUpdater interface.
 *
 * So the fields are for example the data which you want to update on the screen (like the cards on the board)
 */
public class ScreenUpdaterEventArgs{
  public ArrayList<Player> players;
  public ArrayList<Card> board;
  public int moneyOnBoard;
  public int currentBet;
  public int dealerOffset;
  public int roundsEnded;
  public int turnsEnded;
  public int indexOfRaiser;
  public int winnerIndex;
  public int volume;
  public int difficulty;
  public int potsize;
  // extend this if needed, but remember to update the createEventArgs() function in the GameLogic too
  
  
  public ScreenUpdaterEventArgs(ArrayList<Player> players, ArrayList<Card> board, int moneyOnBoard, int currentBet, int dealerOffset, int roundsEnded, int turnsEnded, int indexOfRaiser, int winnerIndex) {
    this.players = players;
    this.board = board;
    this.moneyOnBoard = moneyOnBoard;
    this.currentBet = currentBet;
    this.dealerOffset = dealerOffset;
    this.roundsEnded = roundsEnded;
    this.turnsEnded = turnsEnded;
    this.indexOfRaiser = indexOfRaiser;
    this.winnerIndex = winnerIndex;
  }
  
  @Override
  public String toString() {
    return "ScreenUpdaterEventArgs{" +
        "players=" + players +
        ", board=" + board +
        ", moneyOnBoard=" + moneyOnBoard +
        ", currentBet=" + currentBet +
        ", dealerOffset=" + dealerOffset +
        ", roundsEnded=" + roundsEnded +
        ", turnsEnded=" + turnsEnded +
        ", indexOfRaiser=" + indexOfRaiser +
        ", winnerIndex=" + winnerIndex +
        '}';
  }
}
