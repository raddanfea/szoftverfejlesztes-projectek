package hu.szoftverprojekt.holdemfree.model;

import java.util.ArrayList;

public class Bot extends Player{
  
  private int difficulty;
  
  public Bot(String name, int difficulty) {
    super(name);
    this.difficulty = difficulty;
  }
  
  /**
   * Call this before the getNextAction() is called in the GameLogic, because it will process the player's action.
   * Has to call the setNextAction function!
   * @param board   the cards on the board
   */
  public void think(ArrayList<Card> board /* ide lehet tobb parametert is irni ha kell */) {
    // pl.: if ( i_have_good_cards ) setNextAction( Actions.RAISE_BY(50) );
  }
  
}
