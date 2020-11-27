package hu.szoftverprojekt.holdemfree.controller;



import androidx.appcompat.app.AppCompatActivity;
import hu.szoftverprojekt.holdemfree.R;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;


public class ThemesScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes_screen);

        ImageView imageView, imageView2, imageView3, imageView4, imageView5;
        Button button, button2, button3, button4, button5, back_button, open_game;

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

    }

}
