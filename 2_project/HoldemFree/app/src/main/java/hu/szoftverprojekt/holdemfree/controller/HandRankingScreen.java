package hu.szoftverprojekt.holdemfree.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import hu.szoftverprojekt.holdemfree.R;

public class HandRankingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_ranking);

        String htmlAsString = getString(R.string.handranking);

        WebView webView = (WebView) findViewById(R.id.handranking);
        webView.loadDataWithBaseURL(null, htmlAsString, "text/html", "utf-8", null);
    }
}