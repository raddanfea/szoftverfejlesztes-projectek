package hu.szoftverprojekt.holdemfree.controller;

import androidx.appcompat.app.AppCompatActivity;
import hu.szoftverprojekt.holdemfree.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class NameScreen extends AppCompatActivity {

    private Button start_button, back_button;
    private EditText player_name;
    private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_screen);

        start_button = (Button)findViewById(R.id.start_button);
        back_button = (Button)findViewById(R.id.back_button);
        player_name = (EditText)findViewById(R.id.player_name);
        imageView = (ImageView)findViewById(R.id.imageView);
        player_name.addTextChangedListener(loginTextWatcher);

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenuScreen();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backScreen();
            }
        });
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String name1 = player_name.getText().toString().trim();

            start_button.setEnabled(!name1.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void openMenuScreen() {
        Intent startGame = new Intent(this, Menu.class);
        startActivity(startGame);
    }

    public void backScreen() {
        finish();
        System.exit(0);
    }

}
