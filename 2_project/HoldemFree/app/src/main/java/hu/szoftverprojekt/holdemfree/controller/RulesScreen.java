package hu.szoftverprojekt.holdemfree.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import hu.szoftverprojekt.holdemfree.R;

public class RulesScreen extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_screen);

        WebView webView = (WebView) findViewById(R.id.rules);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setBackgroundColor(0);
        webView.loadUrl("file:///android_asset/" + "rules.html");
    }
}