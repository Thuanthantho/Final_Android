package com.example.bikerer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Access the TextView and set its text
        TextView textViewAdmin = findViewById(R.id.textViewAdmin);
        textViewAdmin.setText("ADMIN");

        Button btnLogOut = findViewById(R.id.btnLogOut);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });

        Button btnUserActivity = findViewById(R.id.btnUserActivity);
        btnUserActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to UserActivity
                Intent intent = new Intent(AdminActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

        Button btnMessage = findViewById(R.id.btnMessage);
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MessageActivity
                Intent intent = new Intent(AdminActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout Confirmation");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform logout and navigate back to HomeActivity
                logoutAndNavigateToHome();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog if "No" is clicked
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void logoutAndNavigateToHome() {
        // Perform logout logic (e.g., Firebase sign out)
        FirebaseAuth.getInstance().signOut();

        // Navigate back to HomeActivity
        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }
}

