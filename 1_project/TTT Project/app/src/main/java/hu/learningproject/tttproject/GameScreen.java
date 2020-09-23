package hu.learningproject.tttproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hu.learningproject.tttproject.view.MyDrawable;
import hu.learningproject.tttproject.view.MyImageView;

public class GameScreen extends AppCompatActivity {

    private MyDrawable mydrawing;
    private MyImageView image;

    int startX, startY, origoX, origoY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        TextView name1 = (TextView) findViewById(R.id.textView1);
        TextView name2 = (TextView) findViewById(R.id.textView2);

        String player1name = getIntent().getStringExtra("p1n");
        String player2name = getIntent().getStringExtra("p2n");

        name1.setText(player1name);
        name2.setText(player2name);
    
        startX = 0;
        startY = 0;
        origoX = 0;
        origoY = 0;
        
        image = (MyImageView) findViewById(R.id.imV);
        mydrawing = new MyDrawable(image, 10, 100);
        
        image.setOnImageViewSizeChanged(new MyImageView.OnImageViewSizeChanged() {
            @Override
            public void invoke(ImageView v, int w, int h) {
                mydrawing.resize(w, h);
                drawGrid(origoX, origoY);
            }
        });
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
            
                int _x = (int)event.getX();
                int _y = (int)event.getY();
            
                Log.d("image_onTouch", "touched at: " + _x + ", " + _y);
                Log.d("image_onTouch", "origo pos: " + origoX + ", " + origoY);
            
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = _x;
                        startY = _y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        origoX += (_x - startX); // magic number
                        origoY += (_y - startY);
                        startX = _x;
                        startY = _y;
                    
                        drawGrid(origoX, origoY);
                    
                        break;
                }
            
                image.invalidate(); // without this the drawing wont show
                return true;
            }
        });

    }
    
    private void drawGrid(int x, int y) {
        mydrawing.fillBackground(Color.rgb(50, 50, 50));
        mydrawing.drawGid(x, y, 200, 5, 7);
    }


}