package hu.szoftverprojekt.holdemfree.controller;

import androidx.appcompat.app.AppCompatActivity;
import hu.szoftverprojekt.holdemfree.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class NameScreen extends AppCompatActivity {

    private Button start_button, back_button;
    private EditText player_name;
    private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_screen);

        start_button = (Button)findViewById(R.id.start_button);
        back_button = (Button)findViewById(R.id.back_button);
        player_name = (EditText)findViewById(R.id.player_name);
        imageView = (ImageView)findViewById(R.id.imageView);

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameScreen();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backScreen();
            }
        });
    }

    public void openGameScreen() {
        Intent startGame = new Intent(this, GameScreen.class);
        startActivity(startGame);
    }

    public void backScreen() {
        finish();
        System.exit(0);
    }

}
