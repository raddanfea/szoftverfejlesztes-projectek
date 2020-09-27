package hu.learningproject.tttproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
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
    public boolean firstPlayersTurn = false;
    
    private int zoom = 200;
    private int startX, startY, origoX, origoY, tempX, tempY, selectedX, selectedY;
    private long touchStartTime;
    private final int UNSELECTED = -1;
    private GameData gameData;

    @Override
    protected void onStart() {
        super.onStart();
        ImageView imageView3 = findViewById(R.id.imageView3);
        ImageView imageView4 = findViewById(R.id.imageView4);
        if(getIntent().hasExtra("image")) {
            imageView3.setImageURI(Uri.parse(getIntent().getStringExtra("image")));
        }
        if(getIntent().hasExtra("image2")) {
            imageView4.setImageURI(Uri.parse(getIntent().getStringExtra("image2")));
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        final TextView name1 = (TextView) findViewById(R.id.textView1);
        final TextView name2 = (TextView) findViewById(R.id.textView2);
    
        gameData = new GameData();
        gameData.p1 = getIntent().getStringExtra("p1n");
        gameData.p2 = getIntent().getStringExtra("p2n");

        name1.setText(gameData.p1);
        name2.setText(gameData.p2);

        name1.setBackgroundColor(Color.parseColor("#E6DB0E"));
        name1.setTypeface(Typeface.DEFAULT_BOLD);
        name1.setTextColor(Color.parseColor("#FFFFFF"));
        name2.setTypeface(Typeface.DEFAULT);
        name1.setTextSize(24);

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
        selectedX = UNSELECTED;
        selectedY = UNSELECTED;
        gameData.winner = 0;
        gameData.turn = 1;
        
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
                        if(gameData.winner != 0)
                            return true;
                        
                        long touchEndTime = System.nanoTime();
                        Log.d("max", "x - startX = " + (_x - startX));
                        // if released under 300ms then ...
                        if(((touchEndTime - touchStartTime)/1000000 <= 300) && ((selectedX != -1) && (selectedY != -1)) && (max(abs(_x - startX),abs(_y - startY)) < zoom/4)) {
                            Log.d("press", "released under 300ms");
                            // check if selected tile is empty
                            if(GameLogic.isValidPos(selectedX, selectedY, gameData.currentMap)) {
                                if (firstPlayersTurn) {
                                    highlightPlayer(name1, name2);
                                    firstPlayersTurn = false;
                                }
                                else {
                                    highlightPlayer(name2, name1);
                                    firstPlayersTurn = true;
                                }
                                // pass the calculated indexes (selectedX, selectedY) to the game
                                int prevLength = gameData.currentMap.length;
                                gameData.currentMap = GameLogic.GetNextStep(selectedX, selectedY, gameData.turn, gameData.currentMap.length, gameData.currentMap);
                                if(GameLogic.isWinner(selectedX, selectedY, gameData.turn, gameData.currentMap)) {
                                    gameData.winner = (byte)(gameData.turn%2 == 0 ? 2 : 1);
                                    Log.d("win", "the winner is: " + gameData.winner);
                                    Toast.makeText(getApplicationContext(), "The winner is: " + (gameData.winner == 1 ? gameData.p1 : gameData.p2), Toast.LENGTH_LONG).show();
                                }
                                
                                if(gameData.currentMap.length > prevLength) {
                                    origoX -= zoom;
                                    origoY -= zoom;
                                    selectedX++;
                                    selectedY++;
                                }
                                drawGrid(gameData.currentMap);
                                gameData.turn++;
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

    private void highlightPlayer (TextView name1, TextView name2) {
        name1.setBackgroundColor(Color.parseColor("#E6DB0E"));
        name1.setTextColor(Color.parseColor("#FFFFFF"));
        name2.setTextColor(Color.parseColor("#D8C0C0"));
        name1.setTypeface(Typeface.DEFAULT_BOLD);
        name2.setBackgroundResource(0);
        name2.setTypeface(Typeface.DEFAULT);
        name1.setTextSize(24);
        name2.setTextSize(18);
    }
    
    private int[] calcGridIndexFromTouch(int x, int y) {
        int[] indexes = new int[2];
        //calc x
        indexes[0] = (x - origoX)/zoom;
        if((x < origoX) || (indexes[0] >= gameData.currentMap.length))
            indexes[0] = UNSELECTED;
        //calc y
        indexes[1] = (y - origoY)/zoom;
        if((y < origoY) || (indexes[1] >= gameData.currentMap[0].length))
            indexes[1] = UNSELECTED;
        return indexes;
    }

    //2 mp alatt "x megnyomva a back gombot vissza leounk az elozo oldalra, ezzel elkerulve a veletlen vissza lepest jatek kozbe
    @Override
    public void onBackPressed() {

        if (BackPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();;
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press BACK to exit", Toast.LENGTH_LONG);
            backToast.show();
        }

        BackPressedTime = System.currentTimeMillis();
    }
}
