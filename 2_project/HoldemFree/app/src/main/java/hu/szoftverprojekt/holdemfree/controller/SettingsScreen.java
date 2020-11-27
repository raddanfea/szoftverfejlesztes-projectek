package hu.szoftverprojekt.holdemfree.controller;



import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import hu.szoftverprojekt.holdemfree.R;
import hu.szoftverprojekt.holdemfree.model.PlaySound;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class SettingsScreen extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        Button back_button = (Button)findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBackScreen();
            }
        });

        Button themes_button = (Button)findViewById(R.id.themes_button);
        themes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openThemesScreen();
            }
        });

        Button game_button = (Button)findViewById(R.id.game_button);
        game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGameScreen();
            }
        });

        TextView volumeValue, potSize, difficultyValue;
        SeekBar volumeBar, potBar, difficultyBar;

        potSize=(TextView)findViewById(R.id.potSize);
        potBar=(SeekBar)findViewById(R.id.potBar);
        potBar.setMax(1200);//ezt később eldöntjük mennyi legyen csak írtam valamit xd
        potBar.setMin(500);

        difficultyValue=(TextView)findViewById(R.id.difficultyValue);
        difficultyBar=(SeekBar)findViewById(R.id.difficultyBar);
        difficultyBar.setMin(1);
        difficultyBar.setMax(3);//ahogy ezt is

        volumeValue=(TextView)findViewById(R.id.volumeValue);
        volumeBar=(SeekBar)findViewById(R.id.volumeBar);

        difficultyBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                difficultyBar.setProgress(progress);
                difficultyValue.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        potBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                potBar.setProgress(progress);
                potSize.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                volumeBar.setProgress(progress);
                volumeValue.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    void openBackScreen() {
        finish();
        System.exit(0);
    }

    void openThemesScreen() {
        Intent target_themes = new Intent(this, ThemesScreen.class);
        startActivity(target_themes);
    }

    void openGameScreen() {
        Intent target_game = new Intent(this, GameScreen.class);
        startActivity(target_game);
    }
}
