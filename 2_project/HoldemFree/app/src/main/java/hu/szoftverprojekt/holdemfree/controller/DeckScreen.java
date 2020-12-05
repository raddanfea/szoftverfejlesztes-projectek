package hu.szoftverprojekt.holdemfree.controller;



import androidx.appcompat.app.AppCompatActivity;
import hu.szoftverprojekt.holdemfree.R;
import hu.szoftverprojekt.holdemfree.data.AppData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class DeckScreen extends AppCompatActivity {

    private Button back_button, gameButton, settingsButton, resetButton, setDefault, setWooden, setIron, setGolden, setDiamond,
            goldenInfo, diamondInfo, woodenInfo, ironInfo;
    private ImageView defaultSkin, wooden, iron, golden, diamond;
    private Button buttons[];
    private TextView winCount;
    private AppData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_screen);

        data = new AppData(this);


        defaultSkin = (ImageView) findViewById(R.id.defaultSkin);
        wooden = (ImageView) findViewById(R.id.wooden);
        iron = (ImageView) findViewById(R.id.iron);
        golden = (ImageView) findViewById(R.id.golden);
        diamond = (ImageView) findViewById(R.id.diamond);

        winCount = (TextView) findViewById(R.id.winCount);
        defaultSkin.setImageResource(R.drawable.k1);
        wooden.setImageResource(R.drawable.wooden_k1);
        iron.setImageResource(R.drawable.leafiron_k1);
        golden.setImageResource(R.drawable.golden_k1);
        diamond.setImageResource(R.drawable.diamond_k1);

        goldenInfo = (Button)findViewById(R.id.goldenInfo);
        diamondInfo = (Button)findViewById(R.id.diamondInfo);
        woodenInfo = (Button)findViewById(R.id.woodenInfo);
        ironInfo = (Button)findViewById(R.id.ironInfo);
        setDefault = (Button) findViewById(R.id.setDefault);
        gameButton = (Button) findViewById(R.id.gameButton);
        settingsButton = (Button) findViewById(R.id.settingsButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        setWooden = (Button) findViewById(R.id.setWooden);
        setIron = (Button) findViewById(R.id.setIron);
        setGolden = (Button) findViewById(R.id.setGolden);
        setDiamond = (Button) findViewById(R.id.setDiamond);
        buttons = new Button[]{setDefault, setWooden, setIron, setGolden, setDiamond};
        back_button = (Button) findViewById(R.id.back_button);
        resetButton = (Button)findViewById(R.id.resetButton);


        //KÁRTYÁK TESZTELÉSÉHEZ
        //DE RESET GOMBHOZ KI KELL VENNI
        /*
        data.save("skinId", 0);
        data.save("ironEnabled", false);
        data.save("wincount", 27);
        */
        winCount.setText("Number of wins: " + data.getInt("wincount"));
        switch (data.getInt("skinId")) {
            case 0:
                setDefault.setEnabled(false);
                setDefault.setText("In use");
                break;
            case 1:
                setWooden.setEnabled(false);
                setWooden.setText("In use");
                break;
            case 2:
                setIron.setEnabled(false);
                setIron.setText("In use");
                break;
            case 3:
                setGolden.setEnabled(false);
                setGolden.setText("In use");
                break;
            case 4:
                setDiamond.setEnabled(false);
                setDiamond.setText("In use");
                break;
        }


        if (data.getInt("wincount") >= 10) {
            setWooden.setEnabled(true);
            woodenInfo.setVisibility(View.GONE);
        } else {
            setWooden.setEnabled(false);
            woodenInfo.setVisibility(View.VISIBLE);
        }

        if (data.getBoolean("ironEnabled")) {
            setIron.setEnabled(true);
            ironInfo.setVisibility(View.GONE);
        } else {
            setIron.setEnabled(false);
            ironInfo.setVisibility(View.VISIBLE);
        }

        if (data.getInt("wincount") >= 25) {
            setGolden.setEnabled(true);
            goldenInfo.setVisibility(View.GONE);
        } else {
            setGolden.setEnabled(false);
            goldenInfo.setVisibility(View.VISIBLE);
        }

        if (data.getInt("wincount") >= 50) {
            setDiamond.setEnabled(true);
            diamondInfo.setVisibility(View.GONE);
        } else {
            setDiamond.setEnabled(false);
            diamondInfo.setVisibility(View.VISIBLE);
        }


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBackScreen();
            }
        });

        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGame();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings();
            }
        });
    }

    private void openBackScreen() {
        finish();
    }

    private void openSettings() {
        Intent target_settings = new Intent(this, SettingsScreen.class);
        startActivity(target_settings);
    }

    private void openGame() {
        Intent target_game = new Intent(this, GameScreen.class);
        startActivity(target_game);
    }

}
