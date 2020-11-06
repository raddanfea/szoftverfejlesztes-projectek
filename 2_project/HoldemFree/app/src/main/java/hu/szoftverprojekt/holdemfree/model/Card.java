package hu.szoftverprojekt.holdemfree.model;

public class Card {
  
  // index of the card
  private int id;
  
  // identifies the skin?
  public int type;
  
  public Card(int id, int type) {
    this.id = id;
    this.type = type;
  }
  
  public int getId() {
    return id;
  }
}
