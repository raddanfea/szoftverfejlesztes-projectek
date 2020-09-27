package hu.learningproject.tttproject;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import hu.learningproject.tttproject.model.PlaySound;


public class NameScreen extends AppCompatActivity {

    private Button game_button;
    private Button back_button;
    private EditText player1name, player2name;
    private ImageView imageView, imageView2;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri, imgUri, imgUri2;
    private boolean firstPicSelected = false;
    private boolean secondPicSelected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_screen);

        player1name = (EditText) findViewById(R.id.p1name);
        player2name = (EditText) findViewById(R.id.p2name);

        player1name.addTextChangedListener(loginTextWatcher);
        player2name.addTextChangedListener(loginTextWatcher);

        game_button = (Button) findViewById(R.id.start_game);
        game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGameScreen();
            }
        });

        back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backScreen();
            }
        });

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker();
                firstPicSelected = true;
                secondPicSelected = false;
            }
        });

        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker();
                secondPicSelected = true;
                firstPicSelected = false;
            }
        });



    }

    public void openGameScreen() {

        String player1_name = player1name.getText().toString();
        String player2_name = player2name.getText().toString();

        Intent target2 = new Intent( this, GameScreen.class);
        target2.putExtra("p1n",player1_name);
        target2.putExtra("p2n",player2_name);
        try {
            target2.putExtra("image", imgUri.toString());
            target2.putExtra("image2", imgUri2.toString());
        }
        catch (NullPointerException e) {
            startActivity(target2);
        }
        startActivity(target2);
    }

    public void backScreen() {
        finish();
        System.exit(0);
    }


    public void imagePicker() {
        Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galery, PICK_IMAGE);
    }


    //start button inactiv until names are not typed
    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String name1 = player1name.getText().toString().trim();
            String name2 = player2name.getText().toString().trim();

            game_button.setEnabled(!name1.isEmpty() && !name2.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            if(firstPicSelected && !secondPicSelected) {
                imageView.setImageURI(imageUri);
                imgUri = imageUri;
            }
            else if (!firstPicSelected && secondPicSelected) {
                imageView2.setImageURI(imageUri);
                imgUri2 = imageUri;
            }
        }
    }
}