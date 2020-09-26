package hu.learningproject.tttproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hu.learningproject.tttproject.model.GameData;
import hu.learningproject.tttproject.model.GameLogic;
import hu.learningproject.tttproject.view.MyDrawable;
import hu.learningproject.tttproject.view.MyImageView;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class GameScreen extends AppCompatActivity {
    
    private MyDrawable mydrawing;
    private MyImageView image;
    private long BackPressedTime;
    private Toast backToast;
    public Button backbutton;
    
    private int zoom = 200;
    private int startX, startY, origoX, origoY, tempX, tempY, selectedX, selectedY;
    private long touchStartTime;
    
    private GameData gameData;
    
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

        backbutton = (Button) findViewById(R.id.back_B);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        
        startX = 0;
        startY = 0;
        origoX = 0;
        origoY = 0;
        tempX = 0;
        tempY = 0;
        selectedX = -1; // -1 = nothing is selected
        selectedY = -1;
        
        gameData = new GameData();
        gameData.currentMap = new byte[gameData.defaultSize][gameData.defaultSize];
        
        image = (MyImageView) findViewById(R.id.imV);
        mydrawing = new MyDrawable(getApplicationContext(), image, 10, 100);
        
        image.setOnImageViewSizeChanged(new MyImageView.OnImageViewSizeChanged() {
            @Override
            public void invoke(ImageView v, int w, int h) {
                mydrawing.resize(w, h);
                drawGrid(gameData.currentMap);
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
                        tempX = startX;
                        tempY = startY;
                        int[] pos = calcGridIndexFromTouch(_x, _y);
                        selectedX = pos[0];
                        selectedY = pos[1];
                        break;
                    case MotionEvent.ACTION_MOVE:
                        origoX += _x - tempX; // magic number
                        origoY += _y - tempY;
                        tempX = _x;
                        tempY = _y;
                        
                        drawGrid(gameData.currentMap);
                        break;
                    case MotionEvent.ACTION_UP:
                        long touchEndTime = System.nanoTime();
                        Log.d("max", "x - startX = " + (_x - startX));
                        // if released under 300ms then ...
                        if(((touchEndTime - touchStartTime)/1000000 <= 300) && ((selectedX != -1) && (selectedY != -1)) && (max(abs(_x - startX),abs(_y - startY)) < zoom/4)) {
                            Log.d("press", "released under 300ms");
                            // check if selected tile is empty
                            if(GameLogic.isValidPos(selectedX, selectedY, gameData.currentMap)) {
                                // pass the calculated indexes (selectedX, selectedY) to the game
                                gameData.currentMap = GameLogic.GetNextStep(selectedX, selectedY, gameData.turn++, gameData.currentMap.length, gameData.currentMap);
                                drawGrid(gameData.currentMap);
                            }
                            
                        }
                        break;
                }
                
                image.invalidate(); // without this the drawing wont show
                return true;
            }
        });
        
    }
    
    private void drawGrid(byte[][] grid) {
        mydrawing.fillBackground(Color.rgb(50, 50, 50));
        mydrawing.drawGid(origoX, origoY, zoom, grid, selectedX, selectedY);
    }
    
    private int[] calcGridIndexFromTouch(int x, int y) {
        int[] indexes = new int[2];
        //calc x
        indexes[0] = (x - origoX)/zoom;
        if((indexes[0] < 0) || (indexes[0] >= gameData.currentMap.length))
            indexes[0] = -1;
        //calc y
        indexes[1] = (y - origoY)/zoom;
        if((indexes[1] < 0) || (indexes[1] >= gameData.currentMap[0].length))
            indexes[1] = -1;
        return indexes;
    }

    //2 mp alatt "x megnyomva a back gonbot vissza leounk az elozo oldalra, ezzel elkerulve a veletlen vissza lepest jatek kozbe
    @Override
    public void onBackPressed() {

        if (BackPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();;
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press Back again to exit", Toast.LENGTH_LONG);
            backToast.show();
        }

        BackPressedTime = System.currentTimeMillis();
    }
}
