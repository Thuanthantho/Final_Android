package com.example.bikerer;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Access the TextView and set its text
        TextView textViewHello = findViewById(R.id.textViewHello);
        textViewHello.setText("Hello");
    }
}
