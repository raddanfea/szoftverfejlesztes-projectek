package hu.szoftverprojekt.holdemfree.model;

import java.util.ArrayList;
import android.util.Log;

/**
 * Game cycle:
 * 1) starts with choosing the dealer, dealing the cards to the players ...
 * 2) the gameplay
 * 3) all 5 cards are on the board or the other players folded, someone wins the money from the board
 * 4) start again with the start() function
 */
public class GameLogic {
  
  private static final String TAG = "testtest";
  private static final int BOT_STARTING_MONEY = 500;

  private ArrayList<Player> players;
  private int round; // starts from 0. A round is all player turns combined, ends when someone takes the money away from the board
  private int turn; // starts from 0. A turn is when a single player can act.
  private ArrayList<Card> board; // only contains the upsided cards
  private int moneyOnBoard;
  private int currentBet; // the current bet a player has to hold
  private int dealerOffset; // index of the player (in the players list) who is the dealer, starts with 0
  private ArrayList<Card> deck; // works like a deck in real life, so in the beginning all the cards are here, but when you add card to a player or put on the board, remove it from this.
  private int difficulty; // 0 is the easiest
  
  /**
   * Call this event when you want to update the screen.
   * Must be implemented in the controller before calling the start() function.
   */
  public ScreenUpdater onChange;
  public ScreenUpdater onGameOver;
  
  /**
   * Create the Bot here.
   * Add the user and the Bot to the players.
   * @param user  a Player instance initialized with name and money
   */
  public GameLogic(Player user) {
  
  }
  
  /**
   * Use this to start the game from the controller class.
   * The ScreenUpdater events should be set before calling this.
   * Calls the init() function at the end.
   */
  public void start() {
  
  }
  
  /**
   * Initializes a game.
   * Fields that needs to be reset:
   * round, board, moneyOnBoard, dealerOffset, deck.
   * Remember that round starts from 0!
   * Has to call the nextRound() function.
   */
  private void init() {
  
  }
  
  /**
   * Call this before every round to:
   *  - reset the turn variable. Remember that it starts from 0.
   *  - put cards on the board
   *  - deal cards to players
   *  - the small and big blinds pays
   *  ...
   */
  private void nextRound() {
    Log.d(TAG, "round " + round);
    turn = 0;
//    currentBet = calcMinBet();
    switch (round) {
      case 0: // preflop
        // dealing 2 card for every player
        for (Player player : players) {
          player.setHand(getNextCardsFromDeck(2));
        }
      
        moneyOnBoard += players.get(dealerOffset+1).pay(calcMinBet()/2) // small blind
            + players.get(dealerOffset+2).pay(calcMinBet()); // big blind
        break;
      case 1: // flop
        board.addAll(getNextCardsFromDeck(3));
        break;
      case 2: // turn
      case 3: // river
        board.add(getNextCardFromDeck());
        break;
    }
  }
  
  /**
   * ... I will do this
   */
  public void nextTurn() {
  
  }
  
  /**
   * @return the data for the onChange() event
   */
  private ScreenUpdaterEventArgs createEventArgs() {
    return  null;
  }
  
  /**
   * @return true if all 5 cards are on the board or the other players folded ...
   */
  private boolean isGameOver() {
    return false;
  }
  
  /**
   * Calls the calculateWinner() function
   * Invokes the onGameOver event
   */
  private void gameOver() {
  
  }
  
  /**
   * Use the calcScoreOfHand() to determine a players score and determine who has more.
   * @return  the index of the winner in the players list
   */
  private static int calculateWinner(ArrayList<Player> players, ArrayList<Card> board) {
    return -1;
  }
  
  /**
   * Higher score means you have better cards.
   * @return  the score of a player
   */
  private static int calcScoreOfHand(ArrayList<Card> board, ArrayList<Card> hand) {
    return -1;
  }
  
  private Card getNextCardFromDeck() {
    return getNextCardsFromDeck(1).get(0);
  }
  
  /**
   * Return given amount of cards from the top of the deck.
   * Remember to remove the choosen cards from the deck!
   * @param amount  amount of cards
   */
  private ArrayList<Card> getNextCardsFromDeck(int amount) {
    return null;
  }
  
  /**
   * Create a new list of cards with all the cards and shuffle them.
   */
  private static ArrayList<Card> generateShuffledDeck() {
    return null;
  }
  
  /**
   * The price of the big blind.
   * The difficulty may alter this value.
   */
  private int calcMinBet() {
    return -1;
  }

  public Player getCurrentPlayer() {
    Log.d(TAG, "the current player is " + players.get(getCurrentPlayerIndex()).getName());
    return players.get(getCurrentPlayerIndex());
  }
  
  public int getCurrentPlayerIndex() {
    return (turn + dealerOffset+3) % players.size();
  }
  
}
