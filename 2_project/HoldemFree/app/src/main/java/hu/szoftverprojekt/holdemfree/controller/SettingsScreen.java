package hu.szoftverprojekt.holdemfree.controller;



import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import hu.szoftverprojekt.holdemfree.R;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        TextView volumeValue;
        SeekBar volumeBar;

        TextView potSize;
        SeekBar potBar;

        potSize=(TextView)findViewById(R.id.potSize);
        potBar=(SeekBar)findViewById(R.id.potBar);
        potBar.setMax(1200);//ezt később eldöntjük mennyi legyen csak írtam valamit xd
        potBar.setMin(500);

        volumeValue=(TextView)findViewById(R.id.volumeValue);
        volumeBar=(SeekBar)findViewById(R.id.volumeBar);


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

}
