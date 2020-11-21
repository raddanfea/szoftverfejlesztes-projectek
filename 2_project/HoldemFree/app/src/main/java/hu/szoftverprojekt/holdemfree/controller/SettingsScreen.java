package hu.szoftverprojekt.holdemfree.controller;



import androidx.appcompat.app.AppCompatActivity;
import hu.szoftverprojekt.holdemfree.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class SettingsScreen extends AppCompatActivity {

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

        TextView volumeValue;
        SeekBar volumeBar;
        volumeValue=(TextView)findViewById(R.id.volumeValue);
        volumeBar=(SeekBar)findViewById(R.id.volumeBar);


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

}
