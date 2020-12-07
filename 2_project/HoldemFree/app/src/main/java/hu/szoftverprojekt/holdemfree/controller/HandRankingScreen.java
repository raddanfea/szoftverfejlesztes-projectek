package hu.szoftverprojekt.holdemfree.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import hu.szoftverprojekt.holdemfree.R;

public class HandRankingScreen extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_ranking);

        WebView webView = (WebView) findViewById(R.id.handranking);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/" + "handranking.html");
        webView.setBackgroundColor(0);

        Button back_button = (Button)findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBackScreen();
            }
        });
    }

    void openBackScreen() {
        Intent target_menu = new Intent(this, Menu.class);
        startActivity(target_menu);
    }
}