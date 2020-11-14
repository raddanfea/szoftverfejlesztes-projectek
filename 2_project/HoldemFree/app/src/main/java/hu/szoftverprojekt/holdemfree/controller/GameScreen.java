package hu.szoftverprojekt.holdemfree.controller;

import androidx.appcompat.app.AppCompatActivity;
import hu.szoftverprojekt.holdemfree.R;
import hu.szoftverprojekt.holdemfree.model.Bot;
import hu.szoftverprojekt.holdemfree.model.GameLogic;
import hu.szoftverprojekt.holdemfree.model.Player;
import hu.szoftverprojekt.holdemfree.model.ScreenUpdater;
import hu.szoftverprojekt.holdemfree.model.ScreenUpdaterEventArgs;
import hu.szoftverprojekt.holdemfree.model.actions.Actions;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class GameScreen extends AppCompatActivity {
    
    private static final String TAG = "testtest";
    
    private boolean canInteract;
    private GameLogic game;
    private ImageView[] cardsOnBoard = new ImageView[5];
    private ImageView[] playerCards = new ImageView[2];
    private TextView aiPot;
    private TextView currentPot;
    private TextView playerPot;
    private Player player;
    private Button foldButton;
    private Button holdButton;
    private Button raiseButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        
        aiPot = findViewById(R.id.ai_pot);
        currentPot = findViewById(R.id.current_pot);
        playerPot = findViewById(R.id.player_pot);
        foldButton = findViewById(R.id.fold);
        holdButton = findViewById(R.id.hold);
        raiseButton = findViewById(R.id.raise);
        
        for (int i = 0; i < 5; i++)
            cardsOnBoard[i] = findViewById(getResId("card"+(i+1), R.id.class));
        
        for (int i = 0; i < 2; i++)
            playerCards[i] = findViewById(getResId("player_card"+(i+1), R.id.class));
        
        /////////////////////////////// DEBUG /////////////////////////////////
        debugBindBotCards();
        ///////////////////////////////////////////////////////////////////////
        
        
        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canInteract)
                    return;
                player.setNextAction(Actions.HOLD);
                game.nextTurn();
            }
        });
        foldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canInteract)
                    return;
                player.setNextAction(Actions.FOLD);
                game.nextTurn();
            }
        });
        raiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canInteract)
                    return;
                player.setNextAction(Actions.RAISE_BY(50));
                game.nextTurn();
            }
        });
        
        initGame();
        game.start();
        
    }
    
    private void initGame() {
        player = new Player("asd", 500);
        game = new GameLogic(player);
        game.onChange = new ScreenUpdater() {
            @Override
            public void invoke(ScreenUpdaterEventArgs gameData) {
                Log.d(TAG, "onChange ... " + gameData.toString());
    
                if(gameData.board.size() == 0) {
                    // clear images
                    for (int i = 0; i < 5; i++) {
                        cardsOnBoard[i].setImageResource(R.drawable.cb);
                    }
                } else {
                    for (int i = 0; i < gameData.board.size(); i++) {
                        cardsOnBoard[i].setImageResource(
                            getResId("k"+gameData.board.get(i).getId(), R.drawable.class));
                    }
                }
                
                for (int i = 0; i < 2; i++) {
                    playerCards[i].setImageResource(
                        getResId("k"+player.getHand().get(i).getId(), R.drawable.class));
                }
                /////////////////////////////// DEBUG /////////////////////////////////

                Integer text = GameLogic.calcScoreOfHand(gameData.board, player.getHand());
                log("cumValue: " + String.valueOf(text));

                debugSetImageForBotCards(gameData);
                ///////////////////////////////////////////////////////////////////////
                
                aiPot.setText("ai pot: " + Integer.toString(gameData.players.get(1).getMoney()));
                currentPot.setText(Integer.toString(gameData.moneyOnBoard));
                playerPot.setText(Integer.toString(player.getMoney()));

                // this will start the next turn
                if (gameData.roundsEnded < 4)
                    handlePlayers(gameData);
            }
        };
        game.onGameOver = new ScreenUpdater() {
            @Override
            public void invoke(ScreenUpdaterEventArgs gameData) {
                Log.d(TAG, "onGameOver");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                game.start();
            }
        };
        
    }
    
    private void handlePlayers(ScreenUpdaterEventArgs e) {
        if (game.getCurrentPlayerIndex() != 0) {
            log("///////////////////////////////////////////////\nBots turn ......................");
            canInteract = false;
            ((Bot) game.getCurrentPlayer()).think(e.board);
            game.nextTurn();
        } else {
            log("///////////////////////////////////////////////\nUsers turn ......................");
            canInteract = true;
        }
    }
    
    public static int getResId(String resName, Class<?> c) {
        
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    private void log(String s) {
        System.out.println(s);
    }
    
    
    // --------------------- DEBUG ---------------------------
    private ImageView[] debugBotCards = new ImageView[2];
    
    private void debugBindBotCards() {
        for (int i = 0; i < 2; i++)
            debugBotCards[i] = findViewById(getResId("debug_bot_card_"+(i+1), R.id.class));
    }
    
    private void debugSetImageForBotCards(ScreenUpdaterEventArgs e) {
        for (int i = 0; i < 2; i++) {
            debugBotCards[i].setImageResource(
                getResId("k"+e.players.get(1).getHand().get(i).getId(), R.drawable.class));
        }
    }
    
}