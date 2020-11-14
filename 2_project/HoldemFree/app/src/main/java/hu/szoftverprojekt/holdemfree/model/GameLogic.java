package hu.szoftverprojekt.holdemfree.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hu.szoftverprojekt.holdemfree.model.actions.Raise;

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
  private static final int LAST_ROUND_NUMBER = 3;
  
  private ArrayList<Player> players;
  private int round; // starts from 0. A round is all player turns combined, ends when someone takes the money away from the board
  private int turn; // starts from 0. A turn is when a single player can act.
  private int startingPlayerIndex;
  private ArrayList<Card> board; // only contains the upsided cards
  private int moneyOnBoard;
  private int currentBet; // the current bet a player has to hold
  private int indexOfRaiser; // the index of the player who raised. -1 means no one raised!
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
   * Init the dealerOffset
   * @param user  a Player instance initialized with name and money
   */
  public GameLogic(Player user) {
    players = new ArrayList<>();
    players.add(user);
    players.add(new Bot("bot", BOT_STARTING_MONEY, 0));
    dealerOffset = -1;
    startingPlayerIndex = 0;
  }
  
  /**
   * Use this to start the game from the controller class.
   * The ScreenUpdater events should be set before calling this.
   * Calls the init() function at the end.
   */
  public void start() {
    log("start ...");
    init();
  }
  
  /**
   * Initializes a game.
   * Fields that needs to be reset:
   * round, board, moneyOnBoard, deck.
   * Remember that round starts from 0!
   * Has to call the nextRound() function.
   */
  private void init() {
    log("init ...");
    round = 0;
    board = new ArrayList<>();
    moneyOnBoard = 0;
    dealerOffset++;
    deck = generateShuffledDeck();
    nextRound();
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
    log("round " + round);
    turn = 0;
    indexOfRaiser = -1;
    switch (round) {
      case 0: // preflop
        // dealing 2 card for every player
        for (Player player : players) {
          player.setHand(getNextCardsFromDeck(2));
        }
        startingPlayerIndex = calcPlayerIndex(dealerOffset+3);
        
        currentBet = calcMinBet();
        moneyOnBoard += players.get(calcPlayerIndex(dealerOffset+1)).pay(calcMinBet()/2) // small blind
            + players.get(calcPlayerIndex(dealerOffset+2)).pay(calcMinBet()); // big blind
        break;
      case 1: // flop
        board.addAll(getNextCardsFromDeck(3));
        break;
      case 2: // turn
      case 3: // river
        board.add(getNextCardFromDeck());
        break;
      case 4: // game over
        onChange.invoke(createEventArgs());
        gameOver();
        return;
    }
    
    onChange.invoke(createEventArgs());
  }
  
  /**
   * @return true if
   *  - everyone folded
   *  - everyone acted but no one raised
   *  - someone raised, but everyone paid
   */
  private boolean isRoundOver() {
    // if everyone folded
    int playersFolded = 0;
    for (Player player : players) {
      log(player.getName() + (player.folded ? " folded" : " not folded"));
      if (player.folded) {
        playersFolded++;
      }
    }
    
    if (playersFolded == players.size()-1) {
      round = LAST_ROUND_NUMBER; // game over
      return true;
    }
    
    // everyone acted but no one raised
    if (indexOfRaiser == -1) {
      if (turn == players.size()-1) {
        return true;
      }
    } else {
      // someone raised, but everyone paid
      if (indexOfRaiser == calcPlayerIndex(getCurrentPlayerIndex()+1) ) {
        return true;
      }
    }
    
    return false;
  }
  
  /**
   * Handles the current players action.
   */
  public void nextTurn() {
    log("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"+getCurrentPlayerIndex() + ". players turn\n - Action: " + getCurrentPlayer().getNextAction().name);
    
    // process action
    switch (getCurrentPlayer().getNextAction().name) {
      case "Fold":
        getCurrentPlayer().folded = true;
        break;
      case "Hold":
        // hold if someone raised, check otherwise
        if (indexOfRaiser != -1) {
          moneyOnBoard += getCurrentPlayer().pay(currentBet);
        }
        break;
      case "Raise":
        currentBet = ((Raise) getCurrentPlayer().getNextAction() ).by;
        moneyOnBoard += getCurrentPlayer().pay(currentBet);
        indexOfRaiser = getCurrentPlayerIndex();
        break;
    }
    
    if (isRoundOver()) {
      round++;
      turn++;
      startingPlayerIndex = getCurrentPlayerIndex();
      nextRound();
      return;
    }
    
    turn++;
    onChange.invoke(createEventArgs());
  }
  
  /**
   * @return the data for the onChange() event
   */
  private ScreenUpdaterEventArgs createEventArgs() {
    return  new ScreenUpdaterEventArgs(players, board, moneyOnBoard, currentBet, dealerOffset, round, turn, indexOfRaiser);
  }
  
  /**
   * Calls the calculateWinner() function
   * Invokes the onGameOver event
   */
  private void gameOver() {
    log("GAME OVER");
    onGameOver.invoke(createEventArgs());
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
  public static int calcScoreOfHand(ArrayList<Card> board, ArrayList<Card> hand) {
    return -1;
  }
  
  private Card getNextCardFromDeck() {
    return deck.remove(0);
  }
  
  /**
   * Return given amount of cards from the top of the deck.
   * Remember to remove the choosen cards from the deck!
   * @param amount  amount of cards
   */
  private ArrayList<Card> getNextCardsFromDeck(int amount) {
    ArrayList<Card> out = new ArrayList<>();
    for (int i = 0; i < amount; i++) {
      out.add( deck.remove(0) );
    }
    return out;
  }
  
  /**
   * Create a new list of cards with all the cards and shuffle them.
   */
  private static ArrayList<Card> generateShuffledDeck() {
    ArrayList<Card> out = new ArrayList<>();

    ArrayList<Integer> deckIndexes = getNumbersInRange(1,53);
    Collections.shuffle(deckIndexes);

    for (int i=1; i<deckIndexes.size(); i++)
    {
      out.add(new Card(deckIndexes.get(i), 0));
    }

    return out;
  }

  /**
   * Generates list of numbers in in range.
   */
  public static ArrayList<Integer> getNumbersInRange(int start, int end) {
    ArrayList<Integer> result = new ArrayList<>();
    for (int i = start; i < end; i++) {
      result.add(i);
    }
    return result;
  }
  /**
   * The price of the big blind.
   * The difficulty may alter this value.
   */
  private int calcMinBet() {
    return 80;
  }
  
  public Player getCurrentPlayer() {
    log("the current player is " + players.get(getCurrentPlayerIndex()).getName());
    return players.get(getCurrentPlayerIndex());
  }
  
  public int getCurrentPlayerIndex() {
    return (startingPlayerIndex + turn) % players.size();
  }
  
  public int calcPlayerIndex(int i) {
    return i%players.size();
  }
  
  private void log(String s) {
    System.out.println(s);
  }
  
}
