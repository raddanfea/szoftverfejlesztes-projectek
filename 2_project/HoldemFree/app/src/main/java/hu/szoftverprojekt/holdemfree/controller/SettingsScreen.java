package hu.szoftverprojekt.holdemfree.controller;



import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import hu.szoftverprojekt.holdemfree.R;
import hu.szoftverprojekt.holdemfree.data.AppData;
import hu.szoftverprojekt.holdemfree.model.MediaVolumeEvent;
import hu.szoftverprojekt.holdemfree.model.PlaySound;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;


public class SettingsScreen extends AppCompatActivity {
    
    private AppData data;
    private Switch muteSwitch;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);
    
        data = new AppData(this);

        data.save("playmusic", true);
        muteSwitch = (Switch)findViewById(R.id.muteSwitch);
        if(!data.getBoolean("playmusic")) {
            muteSwitch.setChecked(true);
        }
        else {
            muteSwitch.setChecked(false);
        }
        muteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switchSound();
            }
        });

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
        potBar.setProgress(data.getInt("pot_size"));
        potSize.setText(""+potBar.getProgress());

        difficultyValue=(TextView)findViewById(R.id.difficultyValue);
        difficultyBar=(SeekBar)findViewById(R.id.difficultyBar);
        difficultyBar.setMin(1);
        difficultyBar.setMax(3);//ahogy ezt is
        difficultyBar.setProgress(data.getInt("difficulty"));
        difficultyValue.setText(""+difficultyBar.getProgress());

        volumeValue=(TextView)findViewById(R.id.volumeValue);
        volumeBar=(SeekBar)findViewById(R.id.volumeBar);
        volumeBar.setProgress(data.getInt("volume"));
        volumeValue.setText("" + volumeBar.getProgress());

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
                data.save("difficulty", seekBar.getProgress());
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
                data.save("pot_size", seekBar.getProgress());
            }
        });

        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                volumeBar.setProgress(progress);
                volumeValue.setText(""+progress);
                EventBus.getDefault().post(new MediaVolumeEvent(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                muteSwitch.setChecked(false);
                startService(new Intent(SettingsScreen.this, PlaySound.class));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                data.save("volume", seekBar.getProgress());
                stopService(new Intent(SettingsScreen.this, PlaySound.class));
            }
        });
    }

    void openBackScreen() {
        finish();
        System.exit(0);
    }

    private void openThemesScreen() {
        Intent target_themes = new Intent(this, ThemesScreen.class);
        startActivity(target_themes);
    }

    private void openGameScreen() {
        Intent target_game = new Intent(this, GameScreen.class);
        startActivity(target_game);
    }

    private void switchSound() {
        if(muteSwitch.isChecked()) {
            data.save("playmusic", false);
        }
        else {
            data.save("playmusic", true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(data.getBoolean("playmusic")) {
            muteSwitch.setChecked(false);
        }
        else
            muteSwitch.setChecked(true);
    }
}
