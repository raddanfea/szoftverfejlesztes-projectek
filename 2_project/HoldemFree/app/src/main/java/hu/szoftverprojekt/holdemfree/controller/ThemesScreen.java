package hu.szoftverprojekt.holdemfree.controller;



import androidx.appcompat.app.AppCompatActivity;
import hu.szoftverprojekt.holdemfree.R;
import hu.szoftverprojekt.holdemfree.data.AppData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class ThemesScreen extends AppCompatActivity {

    private AppData data;
    private Button[] buttons;
    private Button button, button2, button3, button4, button5, back_button, open_game;
    private ImageView imageView, imageView2, imageView3, imageView4, imageView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes_screen);

        data = new AppData(this);

        imageView=(ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.bg);

        imageView2=(ImageView)findViewById(R.id.imageView2);
        imageView2.setImageResource(R.drawable.bg2);

        imageView3=(ImageView)findViewById(R.id.imageView3);
        imageView3.setImageResource(R.drawable.bg3);

        imageView4=(ImageView)findViewById(R.id.imageView4);
        imageView4.setImageResource(R.drawable.bg4);

        imageView5=(ImageView)findViewById(R.id.imageView5);
        imageView5.setImageResource(R.drawable.bg5);

        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);
        back_button = (Button)findViewById(R.id.back_button);
        open_game = (Button)findViewById(R.id.open_game);
        buttons = new Button[] {button, button2, button3, button4, button5};

        switch (data.getInt("bgId")) {
            case 0:
                button.setEnabled(false);
                button.setText("In use");
                break;
            case 1:
                button2.setEnabled(false);
                button2.setText("In use");
                break;
            case 2:
                button3.setEnabled(false);
                button3.setText("In use");
                break;
            case 3:
                button4.setEnabled(false);
                button4.setText("In use");
                break;
            case 4:
                button5.setEnabled(false);
                button5.setText("In use");
                break;
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.isClickable()) {
                    data.save("bgId", 0);
                    changeButtons(0);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.save("bgId", 1);
                changeButtons(1);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.save("bgId", 2);
                changeButtons(2);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.save("bgId", 3);
                changeButtons(3);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.save("bgId", 4);
                changeButtons(4);
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBackScreen();
            }
        });

        open_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGameScreen();
            }
        });


    }



    private void openGameScreen() {
        Intent target_game = new Intent(this, GameScreen.class);
        startActivity(target_game);
    }

    private void openBackScreen() {
        finish();
    }


    private void changeButtons(int buttonId) {
        for(int i=0; i<buttons.length;i++) {
            if (i==buttonId) {
                buttons[i].setText("In use");
                buttons[i].setEnabled(false);
            }
            else {
                buttons[i].setText("Set");
                buttons[i].setEnabled(true);
            }
        }
    }

}
