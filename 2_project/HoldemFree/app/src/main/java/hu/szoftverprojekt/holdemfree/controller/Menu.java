package hu.szoftverprojekt.holdemfree.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import hu.szoftverprojekt.holdemfree.R;
import hu.szoftverprojekt.holdemfree.model.PlaySound;

public class Menu extends AppCompatActivity {

    private long BackPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //draws shadow around the title
        TextView textViewShadow = (TextView) findViewById(R.id.game_title_shadow);
        textViewShadow.getPaint().setStrokeWidth(20);
        textViewShadow.getPaint().setStyle(Paint.Style.STROKE);

        Button change_deck_button = (Button) findViewById(R.id.change_deck);
        change_deck_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeckScreen();
            }
        });

        Button rules_button = (Button) findViewById(R.id.rules);
        rules_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRulesScreen();
            }
        });

        Button settings_button = (Button) findViewById(R.id.settings);
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsScreen();
            }
        });

        Button game_button = (Button) findViewById(R.id.start_game);
        game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameScreen();
            }
        });

        Button handranking_button = (Button) findViewById(R.id.handranking);
        handranking_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHandRankingScreen();
            }
        });


        Button exit_button = (Button) findViewById(R.id.exit);
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitGame();
            }
        });

    }


   private void openDeckScreen () {
        Intent target_change_deck = new Intent(this, DeckScreen.class);
        startActivity(target_change_deck);
    }

   private void openSettingsScreen () {
       Intent target_settings = new Intent(this, SettingsScreen.class);
       startActivity(target_settings);
   }

    private void openGameScreen () {
        Intent target_game = new Intent(this, GameScreen.class);
        startActivity(target_game);
    }

    public void openRulesScreen () {
        Intent target_rules = new Intent(this, RulesScreen.class);
        startActivity(target_rules);
    }

    public void openHandRankingScreen () {
        Intent target_handRanking = new Intent(this, HandRankingScreen.class);
        startActivity(target_handRanking);
    }


    private void exitGame () {

        if (BackPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();;
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press Exit again to close the game", Toast.LENGTH_LONG);
            backToast.show();
        }

        BackPressedTime = System.currentTimeMillis();

    }


    @Override
    protected void onStart() {
        super.onStart();
    }



}