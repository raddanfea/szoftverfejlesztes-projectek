package hu.szoftverprojekt.holdemfree.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import hu.szoftverprojekt.holdemfree.R;
import hu.szoftverprojekt.holdemfree.data.AppData;
import hu.szoftverprojekt.holdemfree.model.Bot;
import hu.szoftverprojekt.holdemfree.model.GameLogic;
import hu.szoftverprojekt.holdemfree.model.MediaVolumeEvent;
import hu.szoftverprojekt.holdemfree.model.PlaySound;
import hu.szoftverprojekt.holdemfree.model.Player;
import hu.szoftverprojekt.holdemfree.model.ScreenUpdater;
import hu.szoftverprojekt.holdemfree.model.ScreenUpdaterEventArgs;
import hu.szoftverprojekt.holdemfree.model.actions.Action;
import hu.szoftverprojekt.holdemfree.model.actions.Actions;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;

public class GameScreen extends AppCompatActivity {
    
    private static final String TAG = "testtest";
    private Toast toast;
    
    private final String[] skinNames = {"", "wooden_", "leafiron_", "golden_", "diamond_"};
    private Thread nextTurnThread;
    private Thread startGameThread;
    private GameLogic game;

    private int winstreak;
    private ConstraintLayout constraintLayout;
    private boolean canInteract;
    private ImageView[] cardsOnBoard = new ImageView[5];
    private ImageView[] playerCards = new ImageView[2];
    private TextView aiPot;
    private TextView currentPot;
    private TextView playerPot;
    private Player player;
    private Button foldButton;
    private Button holdButton;
    private Button raiseButton;
    private AppData data;
    private Switch muteSwitch;
    
    public static class StartGameTask implements Runnable {
        
        private GameLogic game;
        private Player player;
        
        // The object member variable can be shared between multiple threads if the same object instance is passed to MyTask executing in multiple threads
        public StartGameTask() {
        }
        
        public StartGameTask(GameLogic object, Player player) {
            this.game = object;
            this.player = player;
        }
        
        @Override
        public void run() {
            this.game.start();
        }
    }
    public static class NextTurnTask implements Runnable {
        
        private GameLogic game;
        private Player player;
        
        // The object member variable can be shared between multiple threads if the same object instance is passed to MyTask executing in multiple threads
        public NextTurnTask() {
        }
        
        public NextTurnTask(GameLogic object, Player player) {
            this.game = object;
            this.player = player;
        }
        
        @Override
        public void run() {
            this.game.nextTurn();
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        data = new AppData(this);
        constraintLayout = (ConstraintLayout)findViewById(R.id.constraintLayout);
        muteSwitch = (Switch)findViewById(R.id.muteSwitch);
        aiPot = findViewById(R.id.ai_pot);
        currentPot = findViewById(R.id.current_pot);
        playerPot = findViewById(R.id.player_pot);
        foldButton = findViewById(R.id.fold);
        holdButton = findViewById(R.id.hold);
        raiseButton = findViewById(R.id.raise);
        
        toast = new Toast(this);

        switch (data.getInt("bgId")) {
            case 0:
                constraintLayout.setBackgroundResource(R.drawable.bg);
                break;
            case 1:
                constraintLayout.setBackgroundResource(R.drawable.bg2);
                break;
            case 2:
                constraintLayout.setBackgroundResource(R.drawable.bg3);
                break;
            case 3:
                constraintLayout.setBackgroundResource(R.drawable.bg4);
                break;
            case 4:
                constraintLayout.setBackgroundResource(R.drawable.bg5);
                break;

            default:
                constraintLayout.setBackgroundResource(R.drawable.bg);
        }

        if(!data.getBoolean("playmusic")) {
            muteSwitch.setChecked(true);
            stopService(new Intent(GameScreen.this, PlaySound.class));
        }
        else {
            muteSwitch.setChecked(false);
            startService(new Intent(GameScreen.this, PlaySound.class));
        }
        muteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switchSound();
            }
        });

        for (int i = 0; i < 5; i++){
            cardsOnBoard[i] = findViewById(getResId("card"+(i+1), R.id.class));
        }
        
        for (int i = 0; i < 2; i++){
            playerCards[i] = findViewById(getResId("player_card"+(i+1), R.id.class));
//            playerCards[i].setColorFilter(0xB3FFD700, PorterDuff.Mode.SRC_ATOP);
        }
        
        BindBotCards();
        
        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canInteract)
                    return;
                player.setNextAction(Actions.HOLD);
                showAction();
//                game.nextTurn();
                nextTurnThread = new Thread(new NextTurnTask(game, player));
                nextTurnThread.start();
            }
        });
        foldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canInteract)
                    return;
                player.setNextAction(Actions.FOLD);
                showAction();
//                game.nextTurn();
                nextTurnThread = new Thread(new NextTurnTask(game, player));
                nextTurnThread.start();
            }
        });
        raiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canInteract)
                    return;
                player.setNextAction(Actions.RAISE_BY(50));
                showAction();
//                game.nextTurn();
                nextTurnThread = new Thread(new NextTurnTask(game, player));
                nextTurnThread.start();
            }
        });
        
        newGame();
    }
    
    private void newGame() {
        for (int i = 0; i < 2; i++) {
            debugBotCards[i].setImageResource(R.drawable.cb);
        }
        winstreak = 0;
        initGame();
//        game.start();
        startGameThread = new Thread(new StartGameTask(game, player));
        startGameThread.start();
    }
    
    private void initGame() {
        player = new Player("Bob", 500);
        game = new GameLogic(data.getInt("difficulty"), player);
        game.onChange = new ScreenUpdater() {
            @Override
            public void invoke(ScreenUpdaterEventArgs gameData) {
                Log.d(TAG, "onChange ... " + gameData.toString());
    
                if(gameData.board.size() == 0) {
                    // clear images
                    runOnUiThread(new Runnable() {
                        public void run() {
                            for (int i = 0; i < 5; i++) {
                                cardsOnBoard[i].setImageResource(R.drawable.cb);
                                cardsOnBoard[i].invalidate();
                            }
                        }
                    });
                    
                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            for (int i = 0; i < gameData.board.size(); i++) {
                                cardsOnBoard[i].setImageResource(
                                    getResId(skinNames[data.getInt("skinId")] + "k"+gameData.board.get(i).getId(), R.drawable.class));
                                cardsOnBoard[i].invalidate();
                            }
                        }
                    });
                    
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        for (int i = 0; i < 2; i++) {
                            playerCards[i].setImageResource(
                                getResId(skinNames[data.getInt("skinId")] + "k"+player.getHand().get(i).getId(), R.drawable.class));
                            playerCards[i].invalidate();
                        }
                    }
                });
                
                /////////////////////////////// DEBUG /////////////////////////////////

                Integer text = GameLogic.calcScoreOfHand(gameData.board, player.getHand());
                log("cumValue: " + String.valueOf(text));
    
//                SetImageForBotCards(gameData);
                ///////////////////////////////////////////////////////////////////////
                runOnUiThread(new Runnable() {
                    public void run() {
                        aiPot.setText("ai pot: " + Integer.toString(gameData.players.get(1).getMoney()));
                        currentPot.setText(Integer.toString(gameData.moneyOnBoard));
                        playerPot.setText(Integer.toString(player.getMoney()));
                    }
                });
                

                // this will start the next turn
                if (gameData.roundsEnded < 4)
                    handlePlayers(gameData);
            }
        };
        game.onGameOver = new ScreenUpdater() {
            @Override
            public void invoke(ScreenUpdaterEventArgs gameData) {
                Log.d(TAG, "onGameOver");
                if (gameData.winnerIndex == 0) {
                    winstreak += 1;
                    data.save("wincount", data.getInt("wincount") +1 );
                    checkForUnlocks();
                } else {
                    winstreak = 0;
                }
                
                SetImageForBotCards(gameData);
                
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // don't start new game if only one player has money
                int playersLeft = 0;
                for (Player player : gameData.players) {
                    if (player.getMoney() >= GameLogic.calcMinBet()) {
                        playersLeft++;
                    }
                }
    
                if (playersLeft > 1) {
                    ResetImageForBotCards();
                    game.start();
                }
                else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            String winnerMessage = gameData.winnerIndex == 0 ? "Congratulations! You won!" : "I'm sorry. You lost.";
                            AlertDialog dialog = new AlertDialog.Builder(GameScreen.this)
                                .setTitle("Game Over!").setMessage(winnerMessage + "\nDo you want to play again?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        newGame();
                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                })
                                
                                .show();
                        }
                    });
                    
                }
            }
        };
        
    }
    
    private void handlePlayers(ScreenUpdaterEventArgs e) {
        if (game.getCurrentPlayerIndex() != 0) {
            log("///////////////////////////////////////////////\nBots turn ......................");
            canInteract = false;
            ((Bot) game.getCurrentPlayer()).think(e.board);
            showAction();
            game.nextTurn();
        } else {
            log("///////////////////////////////////////////////\nUsers turn ......................");
            canInteract = true;
        }
    }
    
    private void checkForUnlocks() {
        if (winstreak >= 5) {
            data.save("ironEnabled", true);
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
    
    private void showAction() {
        if (game.getCurrentPlayer().getNextAction() == null)
            return;
        String playerText = game.getCurrentPlayerIndex() == 0 ? "You " : "The enemy ";
        String actionText = game.getCurrentPlayer().getNextAction().name + "ed";
        if (game.getCurrentPlayer().getNextAction().name.equals("Raise"))
            actionText += " by 30";
        
        showToast(playerText + actionText);
    }
    
    private void showToast(String s) {
        runOnUiThread(new Runnable() {
            public void run() {
                toast.makeText(GameScreen.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void log(String s) {
        System.out.println(s);
    }
    
    
    // --------------------- DEBUG? ---------------------------
    private ImageView[] debugBotCards = new ImageView[2];
    
    private void BindBotCards() {
        for (int i = 0; i < 2; i++)
            debugBotCards[i] = findViewById(getResId("debug_bot_card_"+(i+1), R.id.class));
    }
    
    private void SetImageForBotCards(ScreenUpdaterEventArgs e) {
        runOnUiThread(new Runnable() {
            public void run() {
                for (int i = 0; i < 2; i++) {
                    debugBotCards[i].setImageResource(
                        getResId(skinNames[data.getInt("skinId")] + "k"+e.players.get(1).getHand().get(i).getId(), R.drawable.class));
                    debugBotCards[i].invalidate();
                }
            }
        });
        
    }
    
    private void ResetImageForBotCards() {
        runOnUiThread(new Runnable() {
            public void run() {
                for (int i = 0; i < 2; i++) {
                    debugBotCards[i].setImageResource(R.drawable.cb);
                    debugBotCards[i].invalidate();
                }
            }
        });
        
    }
    // -------------------------------------------------------

    private void switchSound() {
        if(muteSwitch.isChecked()) {
            data.save("playmusic", false);
            stopService(new Intent(GameScreen.this, PlaySound.class));
        }
        else {
            data.save("playmusic", true);
            startService(new Intent(GameScreen.this, PlaySound.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            stopService(new Intent(GameScreen.this, PlaySound.class));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            stopService(new Intent(GameScreen.this, PlaySound.class));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            stopService(new Intent(GameScreen.this, PlaySound.class));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(data.getBoolean("playmusic")) {
            try {
                startService(new Intent(GameScreen.this, PlaySound.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}