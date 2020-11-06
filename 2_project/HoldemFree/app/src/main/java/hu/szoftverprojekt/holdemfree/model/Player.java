package hu.szoftverprojekt.holdemfree.model;

public class Player {
  
  private String name;
  private int money;
  
  // Set this before the GameLogic processes the player's action (before the getNextAction() is called)
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
