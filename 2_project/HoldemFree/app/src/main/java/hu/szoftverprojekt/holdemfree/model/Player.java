package hu.szoftverprojekt.holdemfree.model;

public class Player {
  
  private String name;
  private int money;
  
  /**
   * Set this in the controller when the user interact's with the game,
   * before the getNextAction() is called in the GameLogic, because it will process the player's action.
   *
   * WARNING: The Bot - which extends this class - will use the think() function to set the value of nextAction.
   */
  private Action nextAction;
  
  
  public Player(String name) {
    this.name = name;
  }

  
  public String getName() {
    return name;
  }
  
  public int getMoney() {
    return money;
  }
  
  public void setMoney(int money) {
    this.money = Math.max(money, 0);
  }
  
  public Action getNextAction() {
    return nextAction;
  }
  
  public void setNextAction(Action nextAction) {
    this.nextAction = nextAction;
  }
}
