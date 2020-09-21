package hu.learningproject.tttproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  private Button play_button;

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
  }

  public void openNameScreen() {
    Intent target = new Intent(this, NameScreen.class);
    startActivity(target);
  }

}