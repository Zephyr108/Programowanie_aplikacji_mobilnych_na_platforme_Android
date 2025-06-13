package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button);
        EditText nameField = (EditText)findViewById(R.id.imie);
        EditText surnameField = (EditText)findViewById(R.id.nazwisko);
        EditText li = (EditText)findViewById(R.id.liczba);

        button.setVisibility(View.INVISIBLE);

        final String[] name = {""};
        final String[] surname = {""};
        final int[] number = {0};

        final Boolean[] nameReady = {false};
        final Boolean[] surnameReady = {false};
        final Boolean[] numberReady = {false};

        Toast recycler = Toast.makeText(this, R.string.tostName, Toast.LENGTH_SHORT);
        Toast recyclerer = Toast.makeText(this, R.string.tostSurname, Toast.LENGTH_SHORT);
        Toast recyclererer = Toast.makeText(this, R.string.tostO, Toast.LENGTH_SHORT);


        nameField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (nameField.getText().toString().isEmpty()) {
                    nameField.setError(getString(R.string.puste));
                    recycler.show();
                    nameReady[0] = false;
                    button.setVisibility(View.INVISIBLE);
                }
                else {
                    name[0] = nameField.getText().toString();
                    nameReady[0] = true;
                    if (surnameReady[0] && numberReady[0])
                        button.setVisibility(View.VISIBLE);
                }
            }
            public void afterTextChanged(Editable s) {

            }
        });

        surnameField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (surnameField.getText().toString().isEmpty()) {
                    surnameField.setError(getString(R.string.puste));
                    recyclerer.show();
                    surnameReady[0] = false;
                    button.setVisibility(View.INVISIBLE);
                }
                else {
                    surname[0] = surnameField.getText().toString();
                    surnameReady[0] = true;
                    if (nameReady[0] && numberReady[0])
                        button.setVisibility(View.VISIBLE);
                }
            }
            public void afterTextChanged(Editable s) {

            }
        });

        li.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (li.getText().toString().isEmpty()) {
                    numberReady[0] = false;
                    li.setError(getString(R.string.puste));
                    button.setVisibility(View.INVISIBLE);
                    return;
                }
                try {
                    number[0] = Integer.parseInt(li.getText().toString());
                } catch (Exception ignored) {}
                if (number[0] > 15 || number[0] < 5) {
                    li.setError(getString(R.string.err));
                    recyclererer.show();
                    numberReady[0] = false;
                    button.setVisibility(View.INVISIBLE);
                }
                else {
                    numberReady[0] = true;
                    if (nameReady[0] && surnameReady[0])
                        button.setVisibility(View.VISIBLE);
                }
            }
            public void afterTextChanged(Editable s) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intention = new Intent(MainActivity.this, GradesInput.class);
                Bundle bundle = new Bundle();
                intention.putExtra("liczba ocen", number[0]);
                startActivityForResult(intention, 1);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    protected void onActivityResult(int kodZad, int kodZak, Intent score) {
        super.onActivityResult(kodZad, kodZak, score);

        try {
            Bundle bundle = score.getExtras();
            float average = bundle.getFloat("srednia");

            TextView shAverage = findViewById(R.id.textAverage);
            shAverage.append(String.valueOf(average));
            shAverage.setVisibility(View.VISIBLE);

            Boolean pass = average > 3f;
            Button button = findViewById(R.id.button);

            if (pass) {
                button.setText(R.string.great);
            } else {
                button.setText(R.string.notGreat);
            }

            Toast passed = Toast.makeText(this, R.string.endP, Toast.LENGTH_SHORT);
            Toast notpassed = Toast.makeText(this, R.string.endNP, Toast.LENGTH_SHORT);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pass) {
                        passed.show();
                    } else {
                        notpassed.show();
                    }

                    finish();
                }
            });
        } catch (Exception ignored) {}

    }
}