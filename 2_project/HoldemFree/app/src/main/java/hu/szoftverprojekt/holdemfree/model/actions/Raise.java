package hu.szoftverprojekt.holdemfree.model.actions;

public class Raise extends Action{
  public int by;
  
  public Raise(int amount) {
    by = amount;
  }
}
