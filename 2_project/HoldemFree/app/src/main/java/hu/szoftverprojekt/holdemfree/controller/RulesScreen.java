package hu.szoftverprojekt.holdemfree.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.webkit.WebView;

import hu.szoftverprojekt.holdemfree.R;

public class RulesScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_screen);

        String htmlAsString = getString(R.string.rules);

        WebView webView = (WebView) findViewById(R.id.rules);
        webView.loadDataWithBaseURL(null, htmlAsString, "text/html", "utf-8", null);
    }
}