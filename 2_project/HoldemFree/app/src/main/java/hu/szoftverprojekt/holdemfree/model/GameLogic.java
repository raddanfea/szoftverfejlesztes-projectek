package hu.szoftverprojekt.holdemfree.model;

import android.os.Build;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
  public ArrayList<Card> board; // only contains the upsided cards
  private int moneyOnBoard;
  private int currentBet; // the current bet a player has to hold
  private int indexOfRaiser; // the index of the player who raised. -1 means no one raised!
  private int dealerOffset; // index of the player (in the players list) who is the dealer, starts with 0
  private ArrayList<Card> deck; // works like a deck in real life, so in the beginning all the cards are here, but when you add card to a player or put on the board, remove it from this.
  private int difficulty; // 0 is the easiest
  private int winnerByFold;
  private int winnerIndex;
  
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
  
    for (Player player : players) {
      player.folded = false;
    }
    winnerByFold = -1;
    
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
    for (int i = 0; i < players.size(); i++) {
      Player player = players.get(i);
      log(player.getName() + (player.folded ? " folded" : " not folded"));
      if (player.folded) {
        playersFolded++;
      } else {
        winnerByFold = i;
      }
    }
    
    if (playersFolded == players.size()-1) {
      round = LAST_ROUND_NUMBER; // game over
      return true;
    }
    winnerByFold = -1;
    
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
    log("players[" + getCurrentPlayerIndex() + "]'s turn\n - Action: " + getCurrentPlayer().getNextAction().name);
    log("                                          SCORE : " + Integer.toString(calcScoreOfHand(board, getCurrentPlayer().getHand())));
  
    // process action
    switch (getCurrentPlayer().getNextAction().name) {
      case "Fold":
        getCurrentPlayer().folded = true;
        break;
      case "Hold":
        if((round == 0) && (getCurrentPlayerIndex() == calcPlayerIndex(dealerOffset+1))) {
          // pay the second half of small blind
          moneyOnBoard += getCurrentPlayer().pay(calcMinBet()/2);
        } else {
          // hold if someone raised, check otherwise
          if (indexOfRaiser != -1) {
            moneyOnBoard += getCurrentPlayer().pay(currentBet);
          }
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
    return  new ScreenUpdaterEventArgs(players, board, moneyOnBoard, currentBet, dealerOffset, round, turn, indexOfRaiser, winnerIndex);
  }
  
  /**
   * Calls the calculateWinner() function
   * Invokes the onGameOver event
   */
  private void gameOver() {
    log("GAME OVER");
    // ha kesz a calcWinner akkor ki lehet venni a kommentet
    if (winnerByFold > -1) {
      winnerIndex = winnerByFold;
    } else {
      winnerIndex = calculateWinner(players, board);
    }

    players.get(winnerIndex).setMoney( players.get(winnerIndex).getMoney() + moneyOnBoard );
    onGameOver.invoke(createEventArgs());
  }
  
  /**
   * Use the calcScoreOfHand() to determine a players score and determine who has more.
   * @return  the index of the winner in the players list
   */
  private static int calculateWinner(ArrayList<Player> players, ArrayList<Card> board) {
    return 0;
  }
  
  /**
   * Higher score means you have better cards.
   * @return  the score of a player
   */
  public static int calcScoreOfHand(ArrayList<Card> board, ArrayList<Card> hand) {

    ArrayList<Card> allCards = new ArrayList<>();
    allCards.addAll(board);
    allCards.addAll(hand);

    List<Integer> cardList = new ArrayList<>(allCards.size());
        for (Card elem : allCards) {
          cardList.add(elem.getId());
        }


    //getting pairs value
    Pair<Integer, Integer> pairs = findPairs(new ArrayList<>(allCards), new ArrayList(cardList));

    //getting drill and poker value
    Pair<Integer, Integer> drills = findDrills(new ArrayList<>(allCards), new ArrayList(cardList));

    //getting straight flush value
    Pair<Integer, Integer> straightF = findStraightFlush(new ArrayList<>(allCards), new ArrayList(cardList));

    //getting straight value
    Pair<Integer, Integer> straight = findStraight(new ArrayList<>(allCards), new ArrayList(cardList));

    //getting flush value
    Pair<Integer, Integer> flush = findFlush(new ArrayList<>(allCards));

      //getting full house value
      Pair<Integer, Integer> fullHouse = findFull(new ArrayList<>(allCards), new ArrayList(cardList), drills.second);

    Integer cumValue = 0;

    // comparing results and giving points
    if (false) { return 0; }
    else if((straightF.first > 0) & straightF.second == 53) {
                                    cumValue += (2500); }                     //royal flush
    else if(straightF.first > 0) {  cumValue += (2000 + straightF.second); }  //straight flush// else if miatt
    else if(drills.first == 2) { cumValue += (1500 + drills.second); }        //poker
    else if(fullHouse.first > 0) { cumValue += (1300 + fullHouse.second); }   //full
    else if(flush.first > 0) {  cumValue += (1000 + flush.second); }          //flush
    else if(straight.first > 0) { cumValue += (750 + straight.second); }      //straight
    else if(drills.first == 1) { cumValue += (500 + drills.second); }         //drills
    else if(pairs.first > 0) {                                                //one and two pairs
          cumValue += (100*pairs.first);
          cumValue += pairs.second;
    }
    return cumValue;
  }

    /**
     * Checks if hand has fullhouse.
     * @return  ? 0:1, highest
     */
    private static Pair<Integer, Integer> findFull(ArrayList<Card> allCards, List<Integer> cardList, int drillVal) {

          int fullVal = 0;
          int fullBest = 0;



          if (drillVal != 0){

              List<Integer> newCardList = new ArrayList<>();

              Log.d("drillval", String.valueOf(drillVal));

              if(drillVal == 53) { drillVal = 1; }

              for(int i = 0; i < cardList.size(); i++){
                  if (getCardTypeValue(cardList.get(i)) != getCardTypeValue(drillVal)) {
                      fullBest = drillVal;
                      newCardList.add(cardList.get(i));
                  }
              }

              Log.d("newcardsize", String.valueOf(newCardList.size()));

              ArrayList<Card> newAllCard = new ArrayList<>();

              for(Card card : allCards){
                  if(newCardList.contains(card.getId())){
                      newAllCard.add(card);
                  }
              }

              Log.d("allcardsize", String.valueOf(allCards.size()));

              Pair<Integer, Integer> pairs = findPairs(newAllCard, new ArrayList(newCardList));
              Log.d("pairlength", String.valueOf(pairs.first));
              if(pairs.first > 0){
                  Log.d("fulval", String.valueOf(pairs.first));
                  fullVal = 1;
                        if (fullBest < pairs.second){
                            fullBest = pairs.second;
                        }

                  Pair<Integer,Integer> p = new Pair(fullVal, fullBest);
                  return p;
              }
          }

          Pair<Integer,Integer> p = new Pair(0, 0);
          return p;
    }


    /**
     * Checks if hand has flush.
     * @return  ? 0:1, highest
     */
    private static Pair<Integer, Integer> findFlush(ArrayList<Card> allCards) {


        List<Integer> c1 = new ArrayList<>();
        List<Integer> c2 = new ArrayList<>();
        List<Integer> c3 = new ArrayList<>();
        List<Integer> c4 = new ArrayList<>();
        int flush = 0;
        int highestFlush = 0;

        for(Card card: allCards){
            if (getCardColor(card.getId()) == 1){   c1.add(card.getId());   }
            else if (getCardColor(card.getId()) == 2){   c2.add(card.getId());   }
            else if (getCardColor(card.getId()) == 3){   c3.add(card.getId());   }
            else if (getCardColor(card.getId()) == 4){   c4.add(card.getId());   }
        }

        if (c1.size()>=5) { flush = 1; highestFlush = Collections.max(c1); }
        else if (c2.size()>=5) { flush = 1; highestFlush = Collections.max(c1); }
        else if (c3.size()>=5) { flush = 1; highestFlush = Collections.max(c1); }
        else if (c4.size()>=5) { flush = 1; highestFlush = Collections.max(c1); }

        /*
        for (int i: c1 ) {
            String str = String.valueOf(i) + getCardColor(i);
            Log.d("c4", str);
        }
        */

        Pair<Integer,Integer> p = new Pair(flush, highestFlush);
        return p;
    }

    /**
     * Checks for card color
     * @return  range of 1-4
     */
    private static int getCardColor(int id) {

        for(int i = 0; i < 13; i++){
            for(int k = 1; k< 5; k++){
                int val = (i*4)+k;
                if (id == val) { return k; }
            }
        }
        return 0;
    }

    /**
     * Checks for card value
     * @return  range of 1-13
     */
    private static int getCardTypeValue(int id){
      for (int i=1; i<14; i++) {
        if (id <= i*4) { return i;}
      }
      return 0;
  }

    /**
     * Checks if hand has straight.
     * @return  ? 0:1, highest
     */
  private static Pair<Integer, Integer> findStraight(ArrayList<Card> allCards,  List<Integer> cardList) {

    int[] cardArray = cardList.stream().mapToInt(i->i).toArray();
    Arrays.sort(cardArray);

    int straight = 0;
    int highestStraight = 0;
    int rangeOf = 1;
    int lastI = -1;
    boolean hasAce = false;

    for(int i=0; i< cardArray.length; i++){

        if (getCardTypeValue(cardArray[i]) == 1) {
            hasAce = true;
        }
        if (getCardTypeValue(cardArray[i]) == lastI+1) {
          rangeOf += 1;
        }
        else if ((getCardTypeValue(cardArray[i]) == 13) & hasAce){
            rangeOf += 1;
        }
        else {
          rangeOf = 1;
        }

        lastI = getCardTypeValue(cardArray[i]);

        if (rangeOf == 5) {

            straight = 1;
            if((allCards.get(i).getId() > highestStraight) | (allCards.get(i).getId() <= 4)) {
                highestStraight = allCards.get(i).getId();
            }
        }
    }

    Pair<Integer,Integer> p = new Pair(straight, highestStraight);
    return p;
  }

    /**
     * Checks if hand has straight flush.
     * @return  ? 0:1, highest
     */
  private static Pair<Integer, Integer> findStraightFlush(ArrayList<Card> allCards, List<Integer> cardList) {

    int[] cardArray = cardList.stream().mapToInt(i->i).toArray();
    Arrays.sort(cardArray);
    int[] tempArray = new int[5];
    int straight = 0;
    int highestStraight = 0;

    for(int k = 0; k < 52-15; k++){
            tempArray[0] = k+1;
            tempArray[1] = k+4+1;
            tempArray[2] = k+8+1;
            tempArray[3] = k+12+1;
            tempArray[4] = k+16+1;
            if(isSubArray(cardArray, tempArray, cardArray.length, tempArray.length)) {
                straight = 1;
                if((allCards.get(k).getId() > highestStraight) | (allCards.get(k).getId() <= 4)){
                    highestStraight = tempArray[4];
                }
            }
    }


    Pair<Integer,Integer> p = new Pair(straight, highestStraight);
    return p;
  }


  /**
   * Checks if B is subarray of A
   * @return  True or False
   */
  static boolean isSubArray(int A[], int B[], int n, int m)
  {
    int i = 0, j = 0;
    while (i < n && j < m)
    {
      if (A[i] == B[j]){
          i++;
          j++;
          if (j == m) return true;
      }
      else {
        i = i-j+1;
        j = 0;
      }
    }
    return false;
  }

  /**
   * Calculates value of drill.
   * Higher score means you have better cards.
   */
  private static Pair<Integer, Integer> findDrills(ArrayList<Card> allCards, List<Integer> cardList) {
    int drill = 0;
    int highestDrill = 0;

    List<Integer> localCardList = cardList;


          while (localCardList.size() > 0) {
              int cardVal = 0;
              int occur = 1;

                  if(allCards.get(cardVal).getId() % 4 == 0){
                      if(localCardList.contains(allCards.get(cardVal).getId()-1)) {
                        occur+=1;
                        if((allCards.get(cardVal).getId() > highestDrill) | (allCards.get(cardVal).getId() <= 4))
                        {highestDrill = allCards.get(cardVal).getId(); } }
                      if(localCardList.contains(allCards.get(cardVal).getId()-2)){
                        occur+=1;
                        if((allCards.get(cardVal).getId() > highestDrill) | (allCards.get(cardVal).getId() <= 4))
                        {highestDrill = allCards.get(cardVal).getId(); } }
                      if(localCardList.contains(allCards.get(cardVal).getId()-3)){
                        occur+=1;
                        if((allCards.get(cardVal).getId() > highestDrill) | (allCards.get(cardVal).getId() <= 4))
                        {highestDrill = allCards.get(cardVal).getId(); }  }
                  }
                  else if(allCards.get(cardVal).getId() % 3 == 0){
                      if(localCardList.contains(allCards.get(cardVal).getId()+1)) {
                        occur+=1;
                        if((allCards.get(cardVal).getId() > highestDrill) | (allCards.get(cardVal).getId() <= 4))
                        {highestDrill = allCards.get(cardVal).getId(); }  }
                      if(localCardList.contains(allCards.get(cardVal).getId()-1)) {
                        occur+=1;
                        if((allCards.get(cardVal).getId() > highestDrill) | (allCards.get(cardVal).getId() <= 4))
                        {highestDrill = allCards.get(cardVal).getId();  }  }
                     if(localCardList.contains(allCards.get(cardVal).getId()-2)) {
                       occur+=1;
                        if((allCards.get(cardVal).getId() > highestDrill) | (allCards.get(cardVal).getId() <= 4))
                        {highestDrill = allCards.get(cardVal).getId();  }  }
                  }
                  else if(allCards.get(cardVal).getId() % 2 == 0){
                      if(localCardList.contains(allCards.get(cardVal).getId()+1)) {
                        occur+=1;
                        if((allCards.get(cardVal).getId() > highestDrill) | (allCards.get(cardVal).getId() <= 4))
                        {highestDrill = allCards.get(cardVal).getId(); }  }
                      if(localCardList.contains(allCards.get(cardVal).getId()+2)) {
                        occur+=1;
                        if((allCards.get(cardVal).getId() > highestDrill) | (allCards.get(cardVal).getId() <= 4))
                        {highestDrill = allCards.get(cardVal).getId(); }  }
                      if(localCardList.contains(allCards.get(cardVal).getId()-1)) {
                        occur+=1;
                        if((allCards.get(cardVal).getId() > highestDrill) | (allCards.get(cardVal).getId() <= 4))
                        {highestDrill = allCards.get(cardVal).getId(); }  }
                  }
                  else {
                      if(localCardList.contains(allCards.get(cardVal).getId()+1)) {
                        occur+=1;
                        if((allCards.get(cardVal).getId() > highestDrill) | (allCards.get(cardVal).getId() <= 4))
                        {highestDrill = allCards.get(cardVal).getId(); }  }
                      if(localCardList.contains(allCards.get(cardVal).getId()+2)) {
                        occur+=1;
                        if((allCards.get(cardVal).getId() > highestDrill) | (allCards.get(cardVal).getId() <= 4))
                        {highestDrill = allCards.get(cardVal).getId(); }  }
                      if(localCardList.contains(allCards.get(cardVal).getId()+3)) {
                        occur+=1;
                        if((allCards.get(cardVal).getId() > highestDrill) | (allCards.get(cardVal).getId() <= 4))
                        {highestDrill = allCards.get(cardVal).getId(); }  }
                  }

                  if (occur == 3) {
                      drill = 1;
                          if ((allCards.get(cardVal).getId() > highestDrill) | (allCards.get(cardVal).getId() <= 4)) {
                              //makes aces the highest
                              highestDrill = allCards.get(cardVal).getId();
                          };
                  }
                  if (occur == 4) {
                      drill = 2;
                      if ((allCards.get(cardVal).getId() > highestDrill) | (allCards.get(cardVal).getId() < 5)) {
                        //makes aces the highest
                        highestDrill = allCards.get(cardVal).getId();
                      };
                    break;
                  }
                  localCardList.remove(0);
          }


    if ((highestDrill < 5) & (highestDrill > 0)) { highestDrill = 53; }
    Pair<Integer,Integer> p = new Pair(drill, highestDrill);
    return p;
  }

  /**
   * Calculates value of pairs and two pairs.
   * Higher score means you have better cards.
   */
  private static Pair<Integer,Integer> findPairs(ArrayList<Card> allCards, List<Integer> cardList) {
    Integer pair = 0;
    Integer highestPair = 0;
    List<Integer> localCardList = cardList;

        for (int i = 0; i < allCards.size(); i++) {
            localCardList.remove(0);
            if(allCards.get(i).getId() % 4 == 0){
                if(localCardList.contains(allCards.get(i).getId()-1)) {pair+=1;
                    if((allCards.get(i).getId() > highestPair) | (allCards.get(i).getId() <= 4)) {highestPair = allCards.get(i).getId();} }
                else if(localCardList.contains(allCards.get(i).getId()-2)){pair+=1;
                  if((allCards.get(i).getId() > highestPair) | (allCards.get(i).getId() <= 4)) {highestPair = allCards.get(i).getId();} }
                else if(localCardList.contains(allCards.get(i).getId()-3)){pair+=1;
                  if((allCards.get(i).getId() > highestPair) | (allCards.get(i).getId() <= 4)) {highestPair = allCards.get(i).getId();}  }
            }
            else if(allCards.get(i).getId() % 3 == 0){
              if(localCardList.contains(allCards.get(i).getId()+1)) {pair+=1;
                if((allCards.get(i).getId() > highestPair) | (allCards.get(i).getId() <= 4)) {highestPair = allCards.get(i).getId();}  }
              else if(localCardList.contains(allCards.get(i).getId()-1)) {pair+=1;
                if((allCards.get(i).getId() > highestPair) | (allCards.get(i).getId() <= 4)) {highestPair = allCards.get(i).getId();}  }
              else if(localCardList.contains(allCards.get(i).getId()-2)) {pair+=1;
                if((allCards.get(i).getId() > highestPair) | (allCards.get(i).getId() <= 4)) {highestPair = allCards.get(i).getId();}  }
            }
            else if(allCards.get(i).getId() % 2 == 0){
              if(localCardList.contains(allCards.get(i).getId()+1)) {pair+=1;
                if((allCards.get(i).getId() > highestPair) | (allCards.get(i).getId() <= 4)) {highestPair = allCards.get(i).getId();}  }
              else if(localCardList.contains(allCards.get(i).getId()+2)) {pair+=1;
                if((allCards.get(i).getId() > highestPair) | (allCards.get(i).getId() <= 4)) {highestPair = allCards.get(i).getId();}  }
              else if(localCardList.contains(allCards.get(i).getId()-1)) {pair+=1;
                if((allCards.get(i).getId() > highestPair) | (allCards.get(i).getId() <= 4)) {highestPair = allCards.get(i).getId();}  }
            }
            else {
              if(localCardList.contains(allCards.get(i).getId()+1)) {pair+=1;
                if((allCards.get(i).getId() > highestPair) | (allCards.get(i).getId() <= 4)) {highestPair = allCards.get(i).getId();}  }
              else if(localCardList.contains(allCards.get(i).getId()+2)) {pair+=1;
                if((allCards.get(i).getId() > highestPair) | (allCards.get(i).getId() <= 4)) {highestPair = allCards.get(i).getId();}  }
              else if(localCardList.contains(allCards.get(i).getId()+3)) {pair+=1;
                if((allCards.get(i).getId() > highestPair) | (allCards.get(i).getId() <= 4)) {highestPair = allCards.get(i).getId();}  }
            }
        }

    if (pair > 2) {pair = 2;}
    if ((highestPair < 5) & (highestPair > 0)) { highestPair = 53; }
    Pair< Integer, Integer > p = new Pair(pair, highestPair);
    return p;
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

    for (int i=0; i<deckIndexes.size(); i++)
    {
      out.add(new Card(deckIndexes.get(i), 0));
    }


    ////////////////FOR TESTS AND DEBUG///////////////
    out.set(0, new Card(1, 0));
    out.set(1, new Card(2, 0));

    out.set(4, new Card(3, 0));
    out.set(5, new Card(17, 0));
    out.set(6, new Card(18, 0));
    out.set(7, new Card(19, 0));
    out.set(8, new Card(49, 0));
    //////////////////////////////////////////////////


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
  public static int calcMinBet() {
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
