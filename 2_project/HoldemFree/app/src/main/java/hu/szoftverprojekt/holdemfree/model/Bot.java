package hu.szoftverprojekt.holdemfree.model;

import java.util.ArrayList;

import hu.szoftverprojekt.holdemfree.model.actions.Actions;

public class Bot extends Player{
  
  private int difficulty;
  private Range[] ranges = {
      new Range(-1, 0),
      new Range(-200, 1500),
      new Range(-700, 2000)
  };
  
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
  
    int ra = (int)(Math.random() * (ranges[difficulty-1].max - ranges[difficulty-1].min + 1) + ranges[difficulty-1].min);
  
    if (score > (749 + ra)) {
      setNextAction(Actions.RAISE_BY(30));
      return;
    }
    
    if ((score < 100) && (score > 0)) {
      setNextAction(Actions.FOLD);
      return;
    }
    setNextAction(Actions.HOLD);
  }
  
  class Range {
    public int min;
    public int max;
    public Range(int min, int max) {
      this.min = min;
      this.max = max;
    }
  }
}
