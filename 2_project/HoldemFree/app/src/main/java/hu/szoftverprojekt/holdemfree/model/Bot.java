package hu.szoftverprojekt.holdemfree.model;

import java.util.ArrayList;

import hu.szoftverprojekt.holdemfree.model.actions.Actions;

public class Bot extends Player{
  
  private int difficulty;
  
  public Bot(String name, int startingMoney, int difficulty) {
    super(name, startingMoney);
    this.difficulty = difficulty;
  }
  
  /**
   * Call this in the nextTurn() method, before the getNextAction() is called in the GameLogic, because it will process the player's action.
   * Has to call the setNextAction function!
   * @param board   the cards on the board
   */
  public void think(ArrayList<Card> board /* ide lehet tobb parametert is irni ha kell */) {
    int score = GameLogic.calcScoreOfHand(board, getHand());
    System.out.println("--------- score: " + score);
    
    if (score > 1000) {
      setNextAction(Actions.RAISE_BY(30));
      return;
    }
    
    if ((score < 200) && (score > 0)) {
      setNextAction(Actions.FOLD);
      return;
    }
    setNextAction(Actions.HOLD);
  }
  
}
