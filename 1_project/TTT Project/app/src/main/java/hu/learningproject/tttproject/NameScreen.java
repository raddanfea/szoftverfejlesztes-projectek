package hu.learningproject.tttproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NameScreen extends AppCompatActivity {

    private Button game_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_screen);

        game_button = (Button) findViewById(R.id.start_game);
        game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGameScreen();
            }
        });
    }

    public void openGameScreen() {
        Intent target2 = new Intent(this, GameScreen.class);
        startActivity(target2);
    }
}