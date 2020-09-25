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

    private int zoom = 200;
    private int startX, startY, origoX, origoY, selectedX, selectedY;
    private long touchStartTime;
    
    // just for testing
    int[][] testGrid;

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
        selectedX = -1; // -1 = nothing is selected
        selectedY = -1;
    
        testGrid = new int[5][7];
        
        testGrid[0][2] = 2;
        testGrid[4][4] = 1;
        testGrid[1][3] = 1;
        
        image = (MyImageView) findViewById(R.id.imV);
        mydrawing = new MyDrawable(getApplicationContext(), image, 10, 100);
        
        image.setOnImageViewSizeChanged(new MyImageView.OnImageViewSizeChanged() {
            @Override
            public void invoke(ImageView v, int w, int h) {
                mydrawing.resize(w, h);
                drawGrid(testGrid);
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
                        touchStartTime = System.nanoTime();
                        startX = _x;
                        startY = _y;
                        int[] pos = calcGridIndexFromTouch(_x, _y);
                        selectedX = pos[0];
                        selectedY = pos[1];
                        break;
                    case MotionEvent.ACTION_MOVE:
                        origoX += _x - startX; // magic number
                        origoY += _y - startY;
                        startX = _x;
                        startY = _y;
                    
                        drawGrid(testGrid);
                        break;
                    case MotionEvent.ACTION_UP:
                        long touchEndTime = System.nanoTime();
                        
                        // if released under 300ms then ...
                        if((touchEndTime - touchStartTime)/1000000 <= 300) {
                            Log.d("press", "released under 300ms");
                            // check if selected tile is empty
                            // pass the calculated indexes (selectedX, selectedY) to the game
                        }
                        break;
                }
            
                image.invalidate(); // without this the drawing wont show
                return true;
            }
        });

    }
    
    private void drawGrid(int[][] grid) {
        mydrawing.fillBackground(Color.rgb(50, 50, 50));
        mydrawing.drawGid(origoX, origoY, zoom, grid, selectedX, selectedY);
    }

    private int[] calcGridIndexFromTouch(int x, int y) {
        int[] indexes = new int[2];
        indexes[0] = (x - origoX)/zoom;
        indexes[1] = (y - origoY)/zoom;
        return indexes;
    }

}