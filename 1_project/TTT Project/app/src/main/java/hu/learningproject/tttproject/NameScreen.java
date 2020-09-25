package hu.learningproject.tttproject;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NameScreen extends AppCompatActivity {

    private Button game_button;
    private Button back_button;

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

        back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backScreen();
            }
        });
    }

    public void openGameScreen() {

        EditText player1name = (EditText) findViewById(R.id.p1name);
        String player1_name = player1name.getText().toString();
        EditText player2name = (EditText) findViewById(R.id.p2name);
        String player2_name = player2name.getText().toString();

        Intent target2 = new Intent( this, GameScreen.class);
        target2.putExtra("p1n",player1_name);
        target2.putExtra("p2n",player2_name);
        startActivity(target2);
    }

    public void backScreen() {
        finish();
        System.exit(0);
    }
}