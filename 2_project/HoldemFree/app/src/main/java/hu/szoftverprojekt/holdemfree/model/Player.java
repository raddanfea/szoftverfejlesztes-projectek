package hu.szoftverprojekt.holdemfree.model;

import java.util.ArrayList;

import hu.szoftverprojekt.holdemfree.model.actions.Action;

public class Player {
  
  private String name;
  private int money;
  private ArrayList<Card> hand;
  public boolean folded;
  
  /**
   * Set this in the controller when the user interact's with the game,
   * before the getNextAction() is called in the GameLogic, because it will process the player's action.
   *
   * WARNING: The Bot - which extends this class - will use the think() function to set the value of nextAction.
   */
  private Action nextAction;
  
  
  public Player(String name, int money) {
    this.name = name;
    this.money = money;
    folded = false;
  }
  
  public int pay(int amount) {
    int paying = Math.min(money, amount);
    money -= paying;
    return paying;
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
  
  public ArrayList<Card> getHand() {
    return hand;
  }
  
  public void setHand(ArrayList<Card> hand) {
    this.hand = hand;
  }
}
