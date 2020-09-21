package hu.learningproject.tttproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class GameScreen extends AppCompatActivity {

    private MyDrawable mydrawing;

    int x, y;
    int startX, startY, origoX, origoY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        final ImageView image = findViewById(R.id.imV);
        mydrawing = new MyDrawable(image);

        startX = 0;
        startY = 0;
        origoX = 0;
        origoY = 0;

        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int _x = (int)event.getX();
                int _y = (int)event.getY();

                Log.d("asd", "touched at: " + _x + ", " + _y);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("asd", "start");
                        startX = _x;
                        startY = _y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        origoX += (_x - startX)/2.63; // magic number
                        origoY += (_y - startY)/2.63;
                        startX = _x;
                        startY = _y;

                        mydrawing.fillBackground(Color.rgb(50, 50, 50));
                        mydrawing.drawGid(origoX, origoY, 80);
                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }

                image.invalidate();
                return true;
            }
        });

    }


}