package hu.learningproject.tttproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  private Button play_button;
  private Button exit_button;
  private Button high_scores_button;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    play_button = (Button) findViewById(R.id.play_button);
    play_button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openNameScreen();
      }
    });
    exit_button = (Button) findViewById(R.id.exit_button);
    exit_button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        System.exit(0);
      }
    });
    high_scores_button = (Button) findViewById(R.id.highscores_button);
    high_scores_button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openHighScores();
      }
    });
  }

  public void openNameScreen() {
    Intent target1 = new Intent(this, NameScreen.class);
    startActivity(target1);
  }

  public void openHighScores() {
    Intent target2 = new Intent(this, HighScores.class);
    startActivity(target2);
  }

}