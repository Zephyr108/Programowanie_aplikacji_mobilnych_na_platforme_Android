package com.example.lab2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddPhoneActivity extends AppCompatActivity {

    private TextInputEditText producerField, modelField, versionField, wwwField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_phone);


        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());


        producerField = findViewById(R.id.editProducer);
        modelField = findViewById(R.id.editModel);
        versionField = findViewById(R.id.editVersion);
        wwwField = findViewById(R.id.editWWW);

        MaterialButton btnSave = findViewById(R.id.btnSave);
        MaterialButton btnCancel = findViewById(R.id.btnCancel);
        MaterialButton btnWeb = findViewById(R.id.btnWeb);

        btnCancel.setOnClickListener(v -> finish());

        btnWeb.setOnClickListener(v -> {
            String url = wwwField.getText().toString().trim();
            if (TextUtils.isEmpty(url)) {
                wwwField.setError("Please enter website address");
                wwwField.requestFocus();
                return;
            }
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                wwwField.setError("URL must start with http:// or https://");
                wwwField.requestFocus();
                return;
            }

            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            } catch (Exception e) {
                Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show();
            }
        });

        btnSave.setOnClickListener(v -> {
            String producer = producerField.getText().toString().trim();
            String model = modelField.getText().toString().trim();
            String version = versionField.getText().toString().trim();
            String www = wwwField.getText().toString().trim();

            boolean hasError = false;

            if (TextUtils.isEmpty(producer)) {
                producerField.setError("Producer is required");
                hasError = true;
            }
            if (TextUtils.isEmpty(model)) {
                modelField.setError("Model is required");
                hasError = true;
            }
            if (TextUtils.isEmpty(version)) {
                versionField.setError("Version is required");
                hasError = true;
            }
            if (TextUtils.isEmpty(www)) {
                wwwField.setError("Website is required");
                hasError = true;
            }

            if (hasError) return;

            Intent resultIntent = new Intent();
            resultIntent.putExtra("producer", producer);
            resultIntent.putExtra("model", model);
            resultIntent.putExtra("version", version);
            resultIntent.putExtra("www", www);

            if (getIntent().hasExtra("id")) {
                resultIntent.putExtra("id", getIntent().getIntExtra("id", -1));
            }

            setResult(RESULT_OK, resultIntent);
            finish();
        });

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            setTitle("Edit phone");
            producerField.setText(intent.getStringExtra("producer"));
            modelField.setText(intent.getStringExtra("model"));
            versionField.setText(intent.getStringExtra("version"));
            wwwField.setText(intent.getStringExtra("www"));
        }
    }
}
