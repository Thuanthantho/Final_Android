package com.example.bikerer;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Retrieve user email from intent
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("userEmail");

        // Find the TextView by ID
        TextView titleTextView = findViewById(R.id.titleTextView);

        // Set the welcome message with the user's email
        String welcomeMessage = "Welcome, " + userEmail;
        titleTextView.setText(welcomeMessage);

        // Find the ImageButton and ImageView by ID
        ImageButton bigCircleButton = findViewById(R.id.bigCircleButton);
        ImageView profileBtn = findViewById(R.id.profileIcon);
        //ImageView messageIcon = findViewById(R.id.messageIcon); // Updated ID

        // Set OnClickListener for the ImageButton
        bigCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to HomeMapsActivity when the circle button is clicked
                Intent mapsIntent = new Intent(HomeActivity.this, HomeMapsActivity.class);
                startActivity(mapsIntent);
            }
        });

        // Set OnClickListener for the profile icon
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener for the message icon
        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }
}

